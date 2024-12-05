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

def customerRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user/createUser')
def customerPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 100001, "username": "emptyCustomer", "address": []}'))
customerRequest.setBodyContent(customerPayload)
addHeaderConfiguration(customerRequest)
def customerResponse = WSBuiltInKeywords.sendRequest(customerRequest)
WSBuiltInKeywords.verifyResponseStatusCode(customerResponse, 200)

def userRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user_username_/updateUser', ['username': 'theUser'])
def userPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 10, "username": "theUser", "firstName": "John", "lastName": "James", "email": "john@email.com", "password": "12345", "phone": "12345", "userStatus": 1}'))
userRequest.setBodyContent(userPayload)
addHeaderConfiguration(userRequest)
def userResponse = WSBuiltInKeywords.sendRequest(userRequest)
WSBuiltInKeywords.verifyResponseStatusCode(userResponse, 200)

def verifyRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user_username_/updateUser', ['username': 'theUser'])
def verifyResponse = WSBuiltInKeywords.sendRequest(verifyRequest)
WSBuiltInKeywords.verifyResponseStatusCode(verifyResponse, 400)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

