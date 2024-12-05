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
def addPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
def addPetPayload = new HttpTextBodyContent(replaceSuffixWithUUID(petPayload))
addPetRequest.setBodyContent(addPetPayload)
addHeaderConfiguration(addPetRequest)
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

if (addPetResponse.getStatusCode() != 200) {
    println("Test failed. Exiting...")
    return
}

def petId = new JsonSlurper().parseText(addPetResponse.getResponseText()).get("id")

def deletePetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet_petId_/deletePet', ["petId": petId])
addHeaderConfiguration(deletePetRequest)
def deletePetResponse = WSBuiltInKeywords.sendRequest(deletePetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(deletePetResponse, 400)

if (deletePetResponse.getStatusCode() == 400) {
    println("Test passed.")
} else {
    println("Test failed.")
}

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

