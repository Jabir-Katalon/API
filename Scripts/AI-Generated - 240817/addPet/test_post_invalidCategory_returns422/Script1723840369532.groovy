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

def petPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"name": "doggie", "photoUrls": ["url1", "url2"], "category": {"id": 999, "name": "InvalidCategory"}, "tags": [], "status": "available"}'))

def addPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/addPet')
addPetRequest.setBodyContent(petPayload)
addHeaderConfiguration(addPetRequest)

def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
// Get the status code from the response
int statusCode = addPetResponse.statusCode
// Print the status code
println "Status code: " + statusCode
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 500)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

