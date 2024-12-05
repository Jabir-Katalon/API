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
def petPayload = '{"name": "doggie", "category": {"id": 1, "name": "Dogs"}, "photoUrls": ["photoUrl1"]}'

def addCategoryRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/addPet')
def addPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/addPet')
def updatePetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/updatePet')

addHeaderConfiguration(addCategoryRequest)
addHeaderConfiguration(addPetRequest)
addHeaderConfiguration(updatePetRequest)

def addCategoryPayload = new HttpTextBodyContent(replaceSuffixWithUUID(categoryPayload))
def addPetPayload = new HttpTextBodyContent(replaceSuffixWithUUID(petPayload))

addCategoryRequest.setBodyContent(addCategoryPayload)
addPetRequest.setBodyContent(addPetPayload)
updatePetRequest.setBodyContent(addPetPayload)

def addCategoryResponse = WSBuiltInKeywords.sendRequest(addCategoryRequest)
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
def updatePetResponse = WSBuiltInKeywords.sendRequest(updatePetRequest)

WSBuiltInKeywords.verifyResponseStatusCode(addCategoryResponse, 200)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)
WSBuiltInKeywords.verifyResponseStatusCode(updatePetResponse, 400)

println(updatePetResponse.getStatusCode())

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

