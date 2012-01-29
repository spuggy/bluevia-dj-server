package bvdj



import org.junit.*
import grails.test.mixin.*

@TestFor(DjController)
@Mock(Dj)
class DjControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/dj/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.djInstanceList.size() == 0
        assert model.djInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.djInstance != null
    }

    void testSave() {
        controller.save()

        assert model.djInstance != null
        assert view == '/dj/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/dj/show/1'
        assert controller.flash.message != null
        assert Dj.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/dj/list'


        populateValidParams(params)
        def dj = new Dj(params)

        assert dj.save() != null

        params.id = dj.id

        def model = controller.show()

        assert model.djInstance == dj
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/dj/list'


        populateValidParams(params)
        def dj = new Dj(params)

        assert dj.save() != null

        params.id = dj.id

        def model = controller.edit()

        assert model.djInstance == dj
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/dj/list'

        response.reset()


        populateValidParams(params)
        def dj = new Dj(params)

        assert dj.save() != null

        // test invalid parameters in update
        params.id = dj.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/dj/edit"
        assert model.djInstance != null

        dj.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/dj/show/$dj.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        dj.clearErrors()

        populateValidParams(params)
        params.id = dj.id
        params.version = -1
        controller.update()

        assert view == "/dj/edit"
        assert model.djInstance != null
        assert model.djInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/dj/list'

        response.reset()

        populateValidParams(params)
        def dj = new Dj(params)

        assert dj.save() != null
        assert Dj.count() == 1

        params.id = dj.id

        controller.delete()

        assert Dj.count() == 0
        assert Dj.get(dj.id) == null
        assert response.redirectedUrl == '/dj/list'
    }
}
