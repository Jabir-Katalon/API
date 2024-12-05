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

def categoryPayload = '{"id": 4, "name": "Fish"}'
def petPayload = '{"id": 13, "name": "fishy", "photoUrls": ["url1"], "category": {"id": 4, "name": "Fish"}, "status": "available"}'
def orderPayload = '{"petId": 13, "quantity": 1, "shipDate": "2022-12-31T23:59:59Z", "status": "invalidStatus", "complete": true}'

def addPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet/addPet')
def placeOrderRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_store_order/placeOrder')

addHeaderConfiguration(addPetRequest)
addHeaderConfiguration(placeOrderRequest)

def addPetPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(petPayload))
addPetRequest.setBodyContent(addPetPayloadContent)

def placeOrderPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(orderPayload))
placeOrderRequest.setBodyContent(placeOrderPayloadContent)

def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
def placeOrderResponse = WSBuiltInKeywords.sendRequest(placeOrderRequest)

WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)
WSBuiltInKeywords.verifyResponseStatusCode(placeOrderResponse, 400)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

