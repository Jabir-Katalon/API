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

def customerPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 100000, "username": "testUser", "address": []}'))
def customerRequest = findTestObject('createUser')
customerRequest.setBodyContent(customerPayload)
addHeaderConfiguration(customerRequest)
def customerResponse = WSBuiltInKeywords.sendRequest(customerRequest)
WSBuiltInKeywords.verifyResponseStatusCode(customerResponse, 200)

def userPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"username": "testUser", "userStatus": "invalid"}'))
def userRequest = findTestObject('createUser')
userRequest.setBodyContent(userPayload)
addHeaderConfiguration(userRequest)
def userResponse = WSBuiltInKeywords.sendRequest(userRequest)
WSBuiltInKeywords.verifyResponseStatusCode(userResponse, 400)

println(userResponse.getStatusCode())

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

