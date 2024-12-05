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

def addPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/addPet')
def addPetPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"name": "doggie", "photoUrls": ["url1", "url2"], "category": {"id": 999, "name": "Invalid Category__unique__"}}'))
addPetRequest.setBodyContent(addPetPayload)
addHeaderConfiguration(addPetRequest)
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

def pet_id = new JsonSlurper().parseText(addPetResponse.getResponseText())['id']

def updatePetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/updatePet')
def updatePetPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": ' + pet_id + ', "name": "doggie", "photoUrls": ["url1", "url2"], "category": {"id": 999, "name": "Invalid Category__unique__"}}'))
updatePetRequest.setBodyContent(updatePetPayload)
addHeaderConfiguration(updatePetRequest)
def updatePetResponse = WSBuiltInKeywords.sendRequest(updatePetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(updatePetResponse, 422)

def statusCode = updatePetResponse.getStatusCode()
println(statusCode)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

