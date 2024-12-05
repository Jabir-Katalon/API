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

// Step 1: Create a new Category
def addCategoryRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/addPet')
def categoryPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 2, "name": "Cats"}'))
addCategoryRequest.setBodyContent(categoryPayload)
addHeaderConfiguration(addCategoryRequest)
def addCategoryResponse = WSBuiltInKeywords.sendRequest(addCategoryRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addCategoryResponse, 200)

// Step 2: Create a new Pet
def addPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/addPet')
def petPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 1, "name": "kitty", "photoUrls": ["url3", "url4"], "category": {"id": 2, "name": "Cats"}, "status": "pending"}'))
addPetRequest.setBodyContent(petPayload)
addHeaderConfiguration(addPetRequest)
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

// Step 3: Send a GET request to /pet/findByStatus?status=pending
def findPetsByStatusRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet_findByStatus/findPetsByStatus')
def queryParams = ['status': 'pending']
def findPetsByStatusRequestWithParams = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet_findByStatus/findPetsByStatus', ['variables': queryParams])
addHeaderConfiguration(findPetsByStatusRequestWithParams)
def findPetsByStatusResponse = WSBuiltInKeywords.sendRequest(findPetsByStatusRequestWithParams)
WSBuiltInKeywords.verifyResponseStatusCode(findPetsByStatusResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

