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

def petPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"name": "doggie__unique__", "photoUrls": ["photoUrl1", "photoUrl2"]}'))
def petRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/addPet')
petRequest.setBodyContent(petPayload)
addHeaderConfiguration(petRequest)
def petResponse = WSBuiltInKeywords.sendRequest(petRequest)
WSBuiltInKeywords.verifyResponseStatusCode(petResponse, 200)
def petId = new JsonSlurper().parseText(petResponse.getResponseText())['id']

def orderPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"petId": ' + petId + ', "quantity": 1, "shipDate": "2022-01-01T00:00:00Z", "status": "approved", "complete": true, "invalidField": "invalidValue"}'))
def orderRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_store_order/placeOrder')
orderRequest.setBodyContent(orderPayload)
addHeaderConfiguration(orderRequest)
def orderResponse = WSBuiltInKeywords.sendRequest(orderRequest)
WSBuiltInKeywords.verifyResponseStatusCode(orderResponse, 422)

println("Test passed: Response status code is 422")

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

