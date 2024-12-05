import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords

def addHeaderConfiguration(request) {
    def content_type_header = new TestObjectProperty("content-type", ConditionType.EQUALS, "application/json")
    request.getHttpHeaderProperties().add(content_type_header)
}

uuid = UUID.randomUUID().toString()

def categoryRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_category/addCategory')
def categoryPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 1, "name": "Dogs"}'))
categoryRequest.setBodyContent(categoryPayload)
addHeaderConfiguration(categoryRequest)
def categoryResponse = WSBuiltInKeywords.sendRequest(categoryRequest)
WSBuiltInKeywords.verifyResponseStatusCode(categoryResponse, 200)

def petRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/addPet')
def petPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 10, "photoUrls": ["url1", "url2"], "category": {"id": 1, "name": "Dogs"}, "tags": [], "status": "available"}'))
petRequest.setBodyContent(petPayload)
addHeaderConfiguration(petRequest)
def petResponse = WSBuiltInKeywords.sendRequest(petRequest)
WSBuiltInKeywords.verifyResponseStatusCode(petResponse, 400)

println(petResponse.getStatusCode())

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

