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

def customerPayload = '{"id": 1, "username": "testCustomer__unique__", "address": []}'
def userPayload = '{"id": 1, "username": "testUser__unique__", "userStatus": "active"}'

def createUserRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user/createUser')
def createCustomerRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user/createUser')

def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(userPayload))
def createCustomerPayload = new HttpTextBodyContent(replaceSuffixWithUUID(customerPayload))

addHeaderConfiguration(createUserRequest)
addHeaderConfiguration(createCustomerRequest)

createUserRequest.setBodyContent(createUserPayload)
createCustomerRequest.setBodyContent(createCustomerPayload)

def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
def createCustomerResponse = WSBuiltInKeywords.sendRequest(createCustomerRequest)

WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)
WSBuiltInKeywords.verifyResponseStatusCode(createCustomerResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

