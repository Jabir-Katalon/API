import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def addHeaderConfiguration(request) {
    def content_type_header = new TestObjectProperty("content-type", ConditionType.EQUALS, "application/json")
    request.getHttpHeaderProperties().add(content_type_header)
}

uuid = UUID.randomUUID().toString()

def testObjects = [
    {
        "testObjectId": "Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet_findByStatus/findPetsByStatus",
        "endpoint": "/pet/findByStatus",
        "method": "get"
    }
]

def responseObject
def expectedStatusCode = 400

testObjects.each { testObject ->
    def request = findTestObject(testObject.testObjectId)
    addHeaderConfiguration(request)

    if (testObject.endpoint.contains("{")) {
        def variables = [:]
        // Set path parameters if needed
        variables.put("petId", "123")
        request = findTestObject(testObject.testObjectId, variables)
    }

    if (testObject.method == "post" || testObject.method == "put") {
        def requestBody = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 0, "name": "doggie", "photoUrls": ["string"], "status": "available"}'))
        request.setBodyContent(requestBody)
    }

    responseObject = WSBuiltInKeywords.sendRequest(request)
    WSBuiltInKeywords.verifyResponseStatusCode(responseObject, expectedStatusCode)
}

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

