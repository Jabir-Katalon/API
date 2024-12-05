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

// Step 1: Create a new Category with id=1 and name='Dogs'
def categoryPayload = '{"id": 1, "name": "Dogs"}'
def categoryRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_category/createCategory')
def categoryPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(categoryPayload))
categoryRequest.setBodyContent(categoryPayloadContent)
addHeaderConfiguration(categoryRequest)
def categoryResponse = WSBuiltInKeywords.sendRequest(categoryRequest)
WSBuiltInKeywords.verifyResponseStatusCode(categoryResponse, 200)

// Step 2: Send a POST request to /pet with an invalid petId (e.g., petId='abc')
def petPayload = '{"id": "abc", "name": "doggie__unique__", "category": ' + categoryPayload + ', "photoUrls": ["url1"], "tags": [], "status": "available"}'
def petRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/addPet')
def petPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(petPayload))
petRequest.setBodyContent(petPayloadContent)
addHeaderConfiguration(petRequest)
def petResponse = WSBuiltInKeywords.sendRequest(petRequest)
WSBuiltInKeywords.verifyResponseStatusCode(petResponse, 400)

// Step 3: Verify that the response status code is 400
if (petResponse.getStatusCode() == 400) {
    println("Step 3 - Response status code is 400 - Test Passed")
} else {
    println("Step 3 - Response status code is not 400 - Test Failed")
}

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

