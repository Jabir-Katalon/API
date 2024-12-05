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

def createCustomerRequest = findTestObject('createWithList')
def createCustomerPayload = new HttpTextBodyContent(replaceSuffixWithUUID('[{"id": 1, "username": "testCustomer__unique__", "address": []}]'))
createCustomerRequest.setBodyContent(createCustomerPayload)
addHeaderConfiguration(createCustomerRequest)
def createCustomerResponse = WSBuiltInKeywords.sendRequest(createCustomerRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createCustomerResponse, 200)

def createUserRequest = findTestObject('getUserByName', ['username': 'testCustomer'])
addHeaderConfiguration(createUserRequest)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def getUserRequest = findTestObject('getUserByName', ['username': 'testCustomer'])
addHeaderConfiguration(getUserRequest)
def getUserResponse = WSBuiltInKeywords.sendRequest(getUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(getUserResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

