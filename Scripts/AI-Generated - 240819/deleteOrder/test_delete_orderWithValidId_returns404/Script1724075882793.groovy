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

// Step 1: Create a new pet
def addPetRequest = findTestObject('_pet/addPet')
addHeaderConfiguration(addPetRequest)
def petPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"name": "doggie__unique__", "photoUrls": ["https://example.com/photo1"]}'))
addPetRequest.setBodyContent(petPayload)
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)
def petId = new JsonSlurper().parseText(addPetResponse.getResponseText())['id']

// Step 2: Place a new order
def placeOrderRequest = findTestObject('_store_order/placeOrder')
addHeaderConfiguration(placeOrderRequest)
def orderPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 1, "petId": ' + petId + ', "quantity": 1, "shipDate": "2022-01-01T00:00:00Z", "status": "placed", "complete": false}'))
placeOrderRequest.setBodyContent(orderPayload)
def placeOrderResponse = WSBuiltInKeywords.sendRequest(placeOrderRequest)
WSBuiltInKeywords.verifyResponseStatusCode(placeOrderResponse, 200)

// Step 3: Retrieve the order details
def orderId = new JsonSlurper().parseText(placeOrderResponse.getResponseText())['id']
def getOrderRequest = findTestObject('_store_order_orderId_/getOrderById', ['orderId': orderId])
addHeaderConfiguration(getOrderRequest)
def getOrderResponse = WSBuiltInKeywords.sendRequest(getOrderRequest)
WSBuiltInKeywords.verifyResponseStatusCode(getOrderResponse, 200)

// Step 4: Delete the order
def deleteOrderRequest = findTestObject('_store_order_orderId_/deleteOrder', ['orderId': orderId])
addHeaderConfiguration(deleteOrderRequest)
def deleteOrderResponse = WSBuiltInKeywords.sendRequest(deleteOrderRequest)
WSBuiltInKeywords.verifyResponseStatusCode(deleteOrderResponse, 404)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

