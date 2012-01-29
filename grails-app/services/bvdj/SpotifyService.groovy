package bvdj

import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*


class SpotifyService {

	static transactional = false

	def getNextTrack(band) {
		
		def spotifyData = getResource('http://ws.spotify.com','/search/1/track.json',[q:band])
	
		def random = new Random()
		def randomInt = random.nextInt(spotifyData.tracks.size())
	
   		return spotifyData.tracks[randomInt]
	
	}


	def getResource(url,path,query=[:]) {
	def http = new HTTPBuilder( url )

	def result = ""

	// perform a GET request, expecting JSON response data
	http.request( GET, JSON ) {
	  uri.path = path
	  uri.query = query
	  
  	  headers.'Accept' = "application/json"

	  // response handler for a success response code:
	  response.success = { resp, json ->
			result = json
	  }
	

	  // handler for any failure status code:
	  response.failure = { resp ->
			throw new RuntimeException("Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}")
	  }
	}
	
	return result 
}
}
