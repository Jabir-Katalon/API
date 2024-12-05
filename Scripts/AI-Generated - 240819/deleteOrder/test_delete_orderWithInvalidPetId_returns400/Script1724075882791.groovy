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

def petPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"name": "doggie__unique__", "photoUrls": ["https://example.com/photo1"]}'))
def addPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
addPetRequest.setBodyContent(petPayload)
addHeaderConfiguration(addPetRequest)
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

def petId = new JsonSlurper().parseText(addPetResponse.getResponseText())['id']

def orderPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 1, "petId": 999999, "quantity": 1, "shipDate": "2022-01-01T00:00:00Z", "status": "placed", "complete": false}'))
def placeOrderRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_store_order/placeOrder')
placeOrderRequest.setBodyContent(orderPayload)
addHeaderConfiguration(placeOrderRequest)
def placeOrderResponse = WSBuiltInKeywords.sendRequest(placeOrderRequest)
WSBuiltInKeywords.verifyResponseStatusCode(placeOrderResponse, 400)

def orderId = new JsonSlurper().parseText(placeOrderResponse.getResponseText())['id']

def deleteOrderRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_store_order_orderId_/deleteOrder', ['orderId': orderId])
addHeaderConfiguration(deleteOrderRequest)
def deleteOrderResponse = WSBuiltInKeywords.sendRequest(deleteOrderRequest)
WSBuiltInKeywords.verifyResponseStatusCode(deleteOrderResponse, 400)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

