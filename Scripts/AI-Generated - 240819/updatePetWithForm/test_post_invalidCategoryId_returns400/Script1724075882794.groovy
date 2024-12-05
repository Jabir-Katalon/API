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

def categoryPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 1, "name": "Dogs"}'))
def addCategoryRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_category/addCategory')
addHeaderConfiguration(addCategoryRequest)
addCategoryRequest.setBodyContent(categoryPayload)
def addCategoryResponse = WSBuiltInKeywords.sendRequest(addCategoryRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addCategoryResponse, 200)

def petPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 10, "name": "doggie", "photoUrls": ["url1", "url2"], "category": {"id": 999, "name": "invalid_category__unique__"}}'))
def addPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
addHeaderConfiguration(addPetRequest)
addPetRequest.setBodyContent(petPayload)
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

def petId = new JsonSlurper().parseText(addPetResponse.getResponseText())['id']

def updatePetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/updatePet')
addHeaderConfiguration(updatePetRequest)
updatePetRequest.setBodyContent(petPayload)
def updatePetResponse = WSBuiltInKeywords.sendRequest(updatePetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(updatePetResponse, 400)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

