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
def categoryRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
def categoryPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(categoryPayload))
categoryRequest.setBodyContent(categoryPayloadContent)
addHeaderConfiguration(categoryRequest)
def categoryResponse = WSBuiltInKeywords.sendRequest(categoryRequest)
WSBuiltInKeywords.verifyResponseStatusCode(categoryResponse, 200)

def petPayload = '{"id": 10, "name": "birdie", "photoUrls": ["url5", "url6"], "category": {"id": 3, "name": "Birds"}, "status": "sold"}'
def petRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
def petPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(petPayload))
petRequest.setBodyContent(petPayloadContent)
addHeaderConfiguration(petRequest)
def petResponse = WSBuiltInKeywords.sendRequest(petRequest)
WSBuiltInKeywords.verifyResponseStatusCode(petResponse, 200)

def getPetByStatusRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet_findByStatus/findPetsByStatus')
def getPetByStatusResponse = WSBuiltInKeywords.sendRequest(getPetByStatusRequest)
WSBuiltInKeywords.verifyResponseStatusCode(getPetByStatusResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

