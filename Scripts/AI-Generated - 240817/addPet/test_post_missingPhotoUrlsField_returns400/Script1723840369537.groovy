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

def categoryRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_category/addCategory')
def categoryPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 1, "name": "Dogs"}'))
categoryRequest.setBodyContent(categoryPayload)
addHeaderConfiguration(categoryRequest)
def categoryResponse = WSBuiltInKeywords.sendRequest(categoryRequest)
WSBuiltInKeywords.verifyResponseStatusCode(categoryResponse, 200)

def petRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/addPet')
def petPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 10, "name": "doggie", "category": {"id": 1, "name": "Dogs__unique__"}, "tags": [], "status": "available"}'))
petRequest.setBodyContent(petPayload)
addHeaderConfiguration(petRequest)
def petResponse = WSBuiltInKeywords.sendRequest(petRequest)
WSBuiltInKeywords.verifyResponseStatusCode(petResponse, 200)

def petId = new JsonSlurper().parseText(petResponse.getResponseText())['id']

def petGetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet_petId_/getPetById', ['petId': petId])
def petGetResponse = WSBuiltInKeywords.sendRequest(petGetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(petGetResponse, 200)

def petMissingPhotoUrlsRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/addPet')
def petPayloadMissingPhotoUrls = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 11, "name": "doggie", "category": {"id": 1, "name": "Dogs__unique__"}, "tags": [], "status": "available"}'))
petMissingPhotoUrlsRequest.setBodyContent(petPayloadMissingPhotoUrls)
addHeaderConfiguration(petMissingPhotoUrlsRequest)
def petMissingPhotoUrlsResponse = WSBuiltInKeywords.sendRequest(petMissingPhotoUrlsRequest)
WSBuiltInKeywords.verifyResponseStatusCode(petMissingPhotoUrlsResponse, 400)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

