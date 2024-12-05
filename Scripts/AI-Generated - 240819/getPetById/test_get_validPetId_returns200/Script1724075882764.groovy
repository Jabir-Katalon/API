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

// Step 1: Create a new Category
def categoryRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
addHeaderConfiguration(categoryRequest)
def categoryPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 1, "name": "Dogs"}'))
categoryRequest.setBodyContent(categoryPayload)
def categoryResponse = WSBuiltInKeywords.sendRequest(categoryRequest)
WSBuiltInKeywords.verifyResponseStatusCode(categoryResponse, 200)

// Step 2: Create a new Pet
def petRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
addHeaderConfiguration(petRequest)
def petPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 10, "name": "doggie", "photoUrls": ["url1", "url2"], "category": {"id": 1, "name": "Dogs"}, "tags": [], "status": "available"}'))
petRequest.setBodyContent(petPayload)
def petResponse = WSBuiltInKeywords.sendRequest(petRequest)
WSBuiltInKeywords.verifyResponseStatusCode(petResponse, 200)

// Step 3: Send a GET request to /pet/10
def getPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet_petId_/getPetById', ['petId': '10'])
addHeaderConfiguration(getPetRequest)
def getPetResponse = WSBuiltInKeywords.sendRequest(getPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(getPetResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

