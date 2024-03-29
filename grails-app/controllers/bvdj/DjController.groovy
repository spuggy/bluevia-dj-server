package bvdj

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class DjController {

	SpotifyService spotifyService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		[djInstanceList: Dj.list(params), djInstanceTotal: Dj.count()]
		
    }
	
	def nexttrack() {
		
		def djList = Dj.where {isdj == true}		
	
		def dj = djList.find();
		
		def nextTrack = spotifyService.getNextTrack(dj.bands)
		
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		render(contentType:"text/json") { trackdata(djdata:dj,track:nextTrack)	}
		
	}


    def create() {
        [djInstance: new Dj(params)]
    }

    def save() {
        def djInstance = new Dj(params)
        if (!djInstance.save(flush: true)) {
            render(view: "create", model: [djInstance: djInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'dj.label', default: 'Dj'), djInstance.id])
        redirect(action: "show", id: djInstance.id)
    }

    def show() {
        def djInstance = Dj.get(params.id)
        if (!djInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'dj.label', default: 'Dj'), params.id])
            redirect(action: "list")
            return
        }

        [djInstance: djInstance]
    }

    def edit() {
        def djInstance = Dj.get(params.id)
        if (!djInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dj.label', default: 'Dj'), params.id])
            redirect(action: "list")
            return
        }

        [djInstance: djInstance]
    }

    def update() {
        def djInstance = Dj.get(params.id)
        if (!djInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dj.label', default: 'Dj'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (djInstance.version > version) {
                djInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'dj.label', default: 'Dj')] as Object[],
                          "Another user has updated this Dj while you were editing")
                render(view: "edit", model: [djInstance: djInstance])
                return
            }
        }

        djInstance.properties = params

        if (!djInstance.save(flush: true)) {
            render(view: "edit", model: [djInstance: djInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'dj.label', default: 'Dj'), djInstance.id])
        redirect(action: "show", id: djInstance.id)
    }

    def delete() {
        def djInstance = Dj.get(params.id)
        if (!djInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'dj.label', default: 'Dj'), params.id])
            redirect(action: "list")
            return
        }

        try {
            djInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'dj.label', default: 'Dj'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'dj.label', default: 'Dj'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
