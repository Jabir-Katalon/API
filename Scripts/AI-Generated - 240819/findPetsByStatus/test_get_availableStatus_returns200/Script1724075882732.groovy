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

def category_payload = '{"id": 1, "name": "Dogs"}'
def pet_payload = '{"id": 10, "name": "doggie", "photoUrls": ["url1", "url2"], "category": ${category_payload}, "status": "available"}'
def get_params = ["status": "available"]

def addCategoryRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
def addPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/updatePet')
def findByStatusRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet_findByStatus/findPetsByStatus')

addHeaderConfiguration(addCategoryRequest)
addHeaderConfiguration(addPetRequest)
addHeaderConfiguration(findByStatusRequest)

addCategoryRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(category_payload)))
addPetRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(pet_payload)))

def addCategoryResponse = WSBuiltInKeywords.sendRequest(addCategoryRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addCategoryResponse, 200)

def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

def findByStatusResponse = WSBuiltInKeywords.sendRequest(findByStatusRequest, get_params)
WSBuiltInKeywords.verifyResponseStatusCode(findByStatusResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

