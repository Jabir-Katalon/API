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

def newPetData = '{"id": 10, "name": "doggie", "category": {"id": 999, "name": "Invalid Category"}, "photoUrls": ["url1", "url2"], "tags": [], "status": "available"}'

def putRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/updatePet')
def putPayload = new HttpTextBodyContent(replaceSuffixWithUUID(newPetData))
putRequest.setBodyContent(putPayload)
addHeaderConfiguration(putRequest)
def putResponse = WSBuiltInKeywords.sendRequest(putRequest)
WSBuiltInKeywords.verifyResponseStatusCode(putResponse, 404)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

