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
        "testObjectId": "Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user/createUser",
        "endpoint": "/user",
        "method": "post"
    },
    {
        "testObjectId": "Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user_username_/getUserByName",
        "endpoint": "/user/{username}",
        "method": "get"
    }
]

// Step 1
def step1TestObject = findTestObject(testObjects[0]['testObjectId'])
addHeaderConfiguration(step1TestObject)
def step1Response = WSBuiltInKeywords.sendRequest(step1TestObject)
assert WSBuiltInKeywords.verifyResponseStatusCode(step1Response, 400), "Step 1 failed: Expected status code 400, but received ${step1Response.getStatusCode()}"

// Step 2
println("Step 2: Verify that the response status code is 400")

// Additional Step for the second test object
def step2TestObject = findTestObject(testObjects[1]['testObjectId'], ['username': 'testuser'])
addHeaderConfiguration(step2TestObject)
def step2Response = WSBuiltInKeywords.sendRequest(step2TestObject)
assert WSBuiltInKeywords.verifyResponseStatusCode(step2Response, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

