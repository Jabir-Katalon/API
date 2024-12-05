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
        "testObjectId": "Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet_findByStatus/findPetsByStatus",
        "endpoint": "/pet/findByStatus",
        "method": "get"
    }
]

def testObject = findTestObject(testObjects[0]['testObjectId'])
addHeaderConfiguration(testObject)

def response = WSBuiltInKeywords.sendRequest(testObject)
WSBuiltInKeywords.verifyResponseStatusCode(response, 404)

println("Step 2 - Verify the response status code is 404: " + response.getStatusCode())

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

