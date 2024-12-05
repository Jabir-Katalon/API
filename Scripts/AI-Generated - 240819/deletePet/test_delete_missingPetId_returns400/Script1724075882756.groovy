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

def petPayload = '{"name": "TestPet__unique__", "photoUrls": ["https://example.com/photo1"]}'
def petRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
def petPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(petPayload))
petRequest.setBodyContent(petPayloadContent)
addHeaderConfiguration(petRequest)
def petResponse = WSBuiltInKeywords.sendRequest(petRequest)
WSBuiltInKeywords.verifyResponseStatusCode(petResponse, 200)
def petId = new JsonSlurper().parseText(petResponse.getResponseText())['id']

def deleteRequestMissingId = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet_petId_/deletePet')
addHeaderConfiguration(deleteRequestMissingId)
def deleteResponseMissingId = WSBuiltInKeywords.sendRequest(deleteRequestMissingId)
WSBuiltInKeywords.verifyResponseStatusCode(deleteResponseMissingId, 404)

def deleteRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet_petId_/deletePet', ['petId': petId])
addHeaderConfiguration(deleteRequest)
def deleteResponse = WSBuiltInKeywords.sendRequest(deleteRequest)
WSBuiltInKeywords.verifyResponseStatusCode(deleteResponse, 400)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

