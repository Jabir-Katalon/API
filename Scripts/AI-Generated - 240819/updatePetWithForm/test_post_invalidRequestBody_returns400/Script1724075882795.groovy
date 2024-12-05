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

def categoryPayload = '{"id": 1, "name": "Dogs"}'
def categoryRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
def categoryPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(categoryPayload))
categoryRequest.setBodyContent(categoryPayloadContent)
addHeaderConfiguration(categoryRequest)
def categoryResponse = WSBuiltInKeywords.sendRequest(categoryRequest)
WSBuiltInKeywords.verifyResponseStatusCode(categoryResponse, 200)

def petPayload = '{"id": 10, "name": "doggie", "photoUrls": ["url1", "url2"], "category": {"id": 1}}'
def petRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
def petPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(petPayload))
petRequest.setBodyContent(petPayloadContent)
addHeaderConfiguration(petRequest)
def petResponse = WSBuiltInKeywords.sendRequest(petRequest)
WSBuiltInKeywords.verifyResponseStatusCode(petResponse, 200)

def invalidPetPayload = '{"id": 10, "name": "doggie", "photoUrls": ["url1", "url2"], "invalid_field": "invalid"}'
def invalidPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
def invalidPetPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(invalidPetPayload))
invalidPetRequest.setBodyContent(invalidPetPayloadContent)
addHeaderConfiguration(invalidPetRequest)
def invalidPetResponse = WSBuiltInKeywords.sendRequest(invalidPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(invalidPetResponse, 400)

def petUpdateRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/updatePet')
def petUpdatePayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(petPayload))
petUpdateRequest.setBodyContent(petUpdatePayloadContent)
addHeaderConfiguration(petUpdateRequest)
def petUpdateResponse = WSBuiltInKeywords.sendRequest(petUpdateRequest)
WSBuiltInKeywords.verifyResponseStatusCode(petUpdateResponse, 200)

def invalidPetUpdateRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/updatePet')
def invalidPetUpdatePayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(invalidPetPayload))
invalidPetUpdateRequest.setBodyContent(invalidPetUpdatePayloadContent)
addHeaderConfiguration(invalidPetUpdateRequest)
def invalidPetUpdateResponse = WSBuiltInKeywords.sendRequest(invalidPetUpdateRequest)
WSBuiltInKeywords.verifyResponseStatusCode(invalidPetUpdateResponse, 400)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

