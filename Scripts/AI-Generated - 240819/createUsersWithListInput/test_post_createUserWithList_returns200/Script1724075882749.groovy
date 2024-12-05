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

def customerPayload = '{"username": "testCustomer"}'
def customerRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_user/createUser')
def customerPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(customerPayload))
customerRequest.setBodyContent(customerPayloadContent)
addHeaderConfiguration(customerRequest)
def customerResponse = WSBuiltInKeywords.sendRequest(customerRequest)
WSBuiltInKeywords.verifyResponseStatusCode(customerResponse, 200)

def usersPayload = '[{"username": "user1"}, {"username": "user2"}, {"username": "user3"}]'
def usersRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_user_createWithList/createUsersWithListInput')
def usersPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(usersPayload))
usersRequest.setBodyContent(usersPayloadContent)
addHeaderConfiguration(usersRequest)
def usersResponse = WSBuiltInKeywords.sendRequest(usersRequest)
WSBuiltInKeywords.verifyResponseStatusCode(usersResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

