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

def petPayload = [
    "id": 10,
    "name": "doggie",
    "category": ["id": 4, "name": "Rabbits"],
    "photoUrls": ["url7", "url8"]
]

def uploadImageEndpoint = "/pet/{petId}/uploadImage"

def addPetRequest = findTestObject('_pet/addPet')
addHeaderConfiguration(addPetRequest)
def addPetPayload = new HttpTextBodyContent(replaceSuffixWithUUID(JsonOutput.toJson(petPayload)))
addPetRequest.setBodyContent(addPetPayload)
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

def petId = addPetResponse.getResponseText('id')

def uploadImageRequest = findTestObject('_pet_petId_uploadImage/uploadFile', ['petId': petId])
addHeaderConfiguration(uploadImageRequest)
def uploadImageResponse = WSBuiltInKeywords.sendRequest(uploadImageRequest)
WSBuiltInKeywords.verifyResponseStatusCode(uploadImageResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

