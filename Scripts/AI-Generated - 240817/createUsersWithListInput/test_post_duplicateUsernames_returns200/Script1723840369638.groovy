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

def customerPayload = '{"id": 1, "username": "testCustomer", "address": []}'
def createCustomerRequest = findTestObject('createUser')
def createCustomerPayload = new HttpTextBodyContent(replaceSuffixWithUUID(customerPayload))
createCustomerRequest.setBodyContent(createCustomerPayload)
addHeaderConfiguration(createCustomerRequest)
def createCustomerResponse = WSBuiltInKeywords.sendRequest(createCustomerRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createCustomerResponse, 200)

def userPayload = '[{"username": "user1", "userStatus": 1}, {"username": "user1", "userStatus": 2}]'
def createUsersRequest = findTestObject('createUsersWithListInput')
def createUsersPayload = new HttpTextBodyContent(replaceSuffixWithUUID(userPayload))
createUsersRequest.setBodyContent(createUsersPayload)
addHeaderConfiguration(createUsersRequest)
def createUsersResponse = WSBuiltInKeywords.sendRequest(createUsersRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUsersResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

