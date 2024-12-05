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

def categoryPayload = '{"id": 3, "name": "Birds"}'
def petPayload = '{"id": 10, "name": "birdie", "photoUrls": [], "category": {"id": 3, "name": "Birds"}, "tags": [], "status": "available"}'

def addCategoryRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
def addPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
def uploadImageRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet_petId_uploadImage/uploadFile')

addHeaderConfiguration(addCategoryRequest)
addHeaderConfiguration(addPetRequest)
addHeaderConfiguration(uploadImageRequest)

def addCategoryPayload = new HttpTextBodyContent(replaceSuffixWithUUID(categoryPayload))
def addPetPayload = new HttpTextBodyContent(replaceSuffixWithUUID(petPayload))

addCategoryRequest.setBodyContent(addCategoryPayload)
addPetRequest.setBodyContent(addPetPayload)

def addCategoryResponse = WSBuiltInKeywords.sendRequest(addCategoryRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addCategoryResponse, 200)

def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

def petId = 10
def uploadImageEndpoint = "/pet/${petId}/uploadImage"
def uploadImageResponse = WSBuiltInKeywords.sendRequest(uploadImageRequest)
WSBuiltInKeywords.verifyResponseStatusCode(uploadImageResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

