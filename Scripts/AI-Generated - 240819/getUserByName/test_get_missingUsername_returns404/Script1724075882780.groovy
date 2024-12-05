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

def userEndpoint = findTestObject("Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_user/createUser")
addHeaderConfiguration(userEndpoint)
def userPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"username": "testuser", "firstName": "Test", "lastName": "User", "email": "testuser@example.com", "password": "test123", "phone": "1234567890"}'))
userEndpoint.setBodyContent(userPayload)
def userResponse = WSBuiltInKeywords.sendRequest(userEndpoint)
WSBuiltInKeywords.verifyResponseStatusCode(userResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

