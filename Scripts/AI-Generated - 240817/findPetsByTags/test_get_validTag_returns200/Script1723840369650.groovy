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
def categoryRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/addPet')
def categoryPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(categoryPayload))
categoryRequest.setBodyContent(categoryPayloadContent)
addHeaderConfiguration(categoryRequest)
def categoryResponse = WSBuiltInKeywords.sendRequest(categoryRequest)
WSBuiltInKeywords.verifyResponseStatusCode(categoryResponse, 200)

def petPayload = '{"id": 1, "name": "Fluffy", "photoUrls": ["url1", "url2"], "category": {"id": 1, "name": "Dogs"}, "tags": [], "status": "available"}'
def petRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/addPet')
def petPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(petPayload))
petRequest.setBodyContent(petPayloadContent)
addHeaderConfiguration(petRequest)
def petResponse = WSBuiltInKeywords.sendRequest(petRequest)
WSBuiltInKeywords.verifyResponseStatusCode(petResponse, 200)

def tags = "tag1"
def findByTagsRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet_findByTags/findPetsByTags')
findByTagsRequest.addParameter("tags", tags)
addHeaderConfiguration(findByTagsRequest)
def findByTagsResponse = WSBuiltInKeywords.sendRequest(findByTagsRequest)
WSBuiltInKeywords.verifyResponseStatusCode(findByTagsResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

