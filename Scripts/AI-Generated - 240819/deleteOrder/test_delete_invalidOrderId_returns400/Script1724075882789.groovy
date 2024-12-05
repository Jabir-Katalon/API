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

def petPayload = '{"name": "doggie__unique__", "photoUrls": ["https://example.com/photo1"]}'
def petRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
def petPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(petPayload))
petRequest.setBodyContent(petPayloadContent)
addHeaderConfiguration(petRequest)
def petResponse = WSBuiltInKeywords.sendRequest(petRequest)
WSBuiltInKeywords.verifyResponseStatusCode(petResponse, 200)
def petId = new JsonSlurper().parseText(petResponse.getResponseText())['id']

def orderPayload = '{"id": 1, "petId": ' + petId + ', "quantity": 1, "shipDate": "2022-01-01T00:00:00Z", "status": "placed", "complete": false}'
def orderRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_store_order/placeOrder')
def orderPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(orderPayload))
orderRequest.setBodyContent(orderPayloadContent)
addHeaderConfiguration(orderRequest)
def orderResponse = WSBuiltInKeywords.sendRequest(orderRequest)
WSBuiltInKeywords.verifyResponseStatusCode(orderResponse, 200)

def invalidOrderId = "invalid_order_id"
def deleteOrderRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_store_order_orderId_/deleteOrder', ['orderId': invalidOrderId])
addHeaderConfiguration(deleteOrderRequest)
def deleteOrderResponse = WSBuiltInKeywords.sendRequest(deleteOrderRequest)
WSBuiltInKeywords.verifyResponseStatusCode(deleteOrderResponse, 400)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

