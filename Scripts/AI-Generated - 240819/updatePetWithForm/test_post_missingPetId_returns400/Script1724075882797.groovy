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
def petPayload = '{"name": "doggie__unique__", "photoUrls": ["url1", "url2"]}'

def createCategoryRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
def createPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
def postPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')

addHeaderConfiguration(createCategoryRequest)
addHeaderConfiguration(createPetRequest)
addHeaderConfiguration(postPetRequest)

createCategoryRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(categoryPayload)))
createPetRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(petPayload)))
postPetRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(petPayload)))

def createCategoryResponse = WSBuiltInKeywords.sendRequest(createCategoryRequest)
def createPetResponse = WSBuiltInKeywords.sendRequest(createPetRequest)
def postPetResponse = WSBuiltInKeywords.sendRequest(postPetRequest)

WSBuiltInKeywords.verifyResponseStatusCode(createCategoryResponse, 200)
WSBuiltInKeywords.verifyResponseStatusCode(createPetResponse, 200)
WSBuiltInKeywords.verifyResponseStatusCode(postPetResponse, 400)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

