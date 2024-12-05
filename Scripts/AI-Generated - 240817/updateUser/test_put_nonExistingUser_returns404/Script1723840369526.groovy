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
addHeaderConfiguration(customerRequest)
def customerPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 100001, "username": "newCustomer", "address": []}'))
customerRequest.setBodyContent(customerPayload)
def customerResponse = WSBuiltInKeywords.sendRequest(customerRequest)
WSBuiltInKeywords.verifyResponseStatusCode(customerResponse, 200)

def userRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user_username_/updateUser', ['username': 'nonExistingUser'])
addHeaderConfiguration(userRequest)
def userPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 11, "username": "nonExistingUser", "firstName": "John", "lastName": "Doe", "email": "john.doe@email.com", "password": "password123", "phone": "1234567890", "userStatus": 1}'))
userRequest.setBodyContent(userPayload)
def userResponse = WSBuiltInKeywords.sendRequest(userRequest)
WSBuiltInKeywords.verifyResponseStatusCode(userResponse, 404)

def responseStatusCode = userResponse.getStatusCode()
println("Step 3 - Response Status Code: ${responseStatusCode}")

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

