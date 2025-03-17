import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import org.testng.Assert;

public class GraphQL_Script {

	public static void main(String[] args) {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		//Query
		String Character ="13230";
		String Queryresponse = given().log().all().header("content-type", "application/json")
				.body("{\r\n" + 
						"  \"query\": \"query ($characterID: Int!, $episodeID: Int!) {\\n  character(characterId: $characterID) {\\n    name\\n    status\\n  }\\n  characters(filters: {name: \\\"Rahul\\\"}) {\\n    info {\\n      count\\n    }\\n  }\\n  \\n  location(locationId: 19225) {\\n    name\\n  }\\n  episode(episodeId: $episodeID) {\\n    id\\n  }\\n  episodes {\\n    info {\\n      count\\n    }\\n  }\\n}\\n\",\r\n" + 
						"  \"variables\": {\r\n" + 
						"    \"characterID\": "+Character+",\r\n" + 
						"    \"episodeID\": 13512\r\n" + 
						"  }\r\n" + 
						"}")
		.when().post("/gq/graphql")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(Queryresponse);
		
		JsonPath js = new JsonPath(Queryresponse);
		String ActualCharacterName = js.get("data.character.name");
		Assert.assertEquals(ActualCharacterName, "KL");
		
		//mutation
		String CharacterName = "Jadeja";
		String Mutationresponse = given().log().all().header("content-type", "application/json")
				.body("{\"query\":\"mutation($LocationName: String!, $CharacterName: String!, $EpisodeName: String!) {\\n  \\n  createLocation(location: {name: $LocationName, type: \\\"South\\\", dimension: \\\"123\\\"}) {\\n    id\\n  }\\n  createCharacter(character: {name: $CharacterName, type:\\\"Batsmen\\\", status:\\\"working\\\", species:\\\"human\\\", gender: \\\"male\\\", image: \\\"cool\\\", originId: 19224, locationId: 19224}) {\\n    id\\n  }\\n  \\n  createEpisode(episode: {name: $EpisodeName, air_date: \\\"March 27\\\", episode: \\\"8\\\"}) {\\n    id\\n  }\\n  \\n  deleteLocations(locationIds:[19226, 19225] ) {\\n    locationsDeleted\\n  }\\n}\",\"variables\":{\"LocationName\":\"Brazil\",\"CharacterName\":\""+CharacterName+"\",\"EpisodeName\":\"Kangroos\"}}")
		.when().post("/gq/graphql")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(Mutationresponse);
	}

}
