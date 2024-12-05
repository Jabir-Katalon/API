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
def petPayload = '{"id": 1, "name": "Tweety", "photoUrls": ["url5", "url6"], "category": {"id": 3, "name": "Birds"}, "tags": ["tag1", "tag2"], "status": "available"}'
def tags = "tag1,tag2"

def addCategoryRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/addPet')
def addPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/addPet')
def findByTagsRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet_findByTags/findPetsByTags')

addHeaderConfiguration(addCategoryRequest)
addHeaderConfiguration(addPetRequest)
addHeaderConfiguration(findByTagsRequest)

addCategoryRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(categoryPayload)))
addPetRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(petPayload)))

def addCategoryResponse = WSBuiltInKeywords.sendRequest(addCategoryRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addCategoryResponse, 200)

def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

def findByTagsResponse = WSBuiltInKeywords.sendRequest(findByTagsRequest)
WSBuiltInKeywords.verifyResponseStatusCode(findByTagsResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

