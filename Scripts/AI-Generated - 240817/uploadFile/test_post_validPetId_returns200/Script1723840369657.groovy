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

def petPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"name": "Fluffy__unique__", "photoUrls": ["https://example.com/photo.jpg"], "status": "available"}'))
def addPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/addPet')
addPetRequest.setBodyContent(petPayload)
addHeaderConfiguration(addPetRequest)
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

def petId = new JsonSlurper().parseText(addPetResponse.getResponseText())['id']

def uploadImagePayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"additionalMetadata": "image metadata", "file": "https://example.com/image.jpg"}'))
def uploadImageRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet_petId_uploadImage/uploadFile', ['petId': petId])
uploadImageRequest.setBodyContent(uploadImagePayload)
addHeaderConfiguration(uploadImageRequest)
def uploadImageResponse = WSBuiltInKeywords.sendRequest(uploadImageRequest)
WSBuiltInKeywords.verifyResponseStatusCode(uploadImageResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

