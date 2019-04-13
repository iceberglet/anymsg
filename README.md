# Anymsg

Anymsg is a lightweight, performant, highly customizable, upgradable Java object 
serialization and deserialization library. It is ByteBuffer based and the Java
object format is similar to FIX protocol (field and group based)

### Why Anymsg

Here is a simple comparison between well known serialization libraries.
You may pick the one that best suits your need
(Using japanese grading standard : S > A > B > C)

Frameworks | Java native | Kryo | SBE | Gson | XML | Protobuf | AnyMsg
--- | --- | --- | --- | --- | --- | --- | ---
Speed                    | C | B | S | C | C | A | S
Accessibility            | S | S | C | S | S | S | A
Type strict              | S | S | C | C | C | S | S
Less Garbage             | C | C | S | C | C | A | S
Versioning support       | C | C | A | A | A | A | S
Language Agnostic        | C | C | S | S | S | S | B
Customizable field types | S | S | C | S | C | A | S

Criteria explanation:
- Speed: How fast can it do serialization / deserialization?
- Accessibility: Code-wise, how easy is it to retrieve a piece of information from its message?
- Type strict: Does the library tolerate type coercion? e.g. pass in a double then read as String?
- Less Garbage: Does the library create more or less garbage?
- Versioning support: How easy is it to add/remove/update fields in a message?
- Language agnostic: Can I define message contract and use it in other programming languages?
- Customizable field types: Can I pass in custom Java objects into the message?

### Getting Started

#### Define your message contracts

Before you do any serialization, you need to define your message contracts. Each
type of message needs its own contract.

Define the fields. The fields define the tag number and the name (which must be
unique in any message!) as well as whether it must be present, and how to encode/decode
it. Normally these definitions can be static and do not carry any mutable runtime
information, but developers are free to choose to implement them in any way
they like.
```java
    private static final MessageDef CLIENT_ORDER = new MessageDef("CLIENT_ORDER", 0, false);
    private static final StringDef CLIENT_ORDER_ID = new StringDef("Client Order ID", 1, false);
    private static final IntegerDef ORDER_QTY = new IntegerDef("Order Quantity", 2, false);
    private static final DoubleDef ORDER_PRICE = new DoubleDef("Order Price", 3, false);
    private static final StringDef PRODUCT_ID = new StringDef("Product ID", 4, false);
```

Define the message schema / contract
```java
    AnyMsg anyMsg = new AnyMsg(MessageImpl::new);
    MessageContract clientOrder = anyMsg.defineMessage(CLIENT_ORDER)
            .addFieldDef(CLIENT_ORDER_ID)
            .addFieldDef(ORDER_QTY)
            .addFieldDef(ORDER_PRICE)
            .addFieldDef(PRODUCT_ID)
            .build();
    anyMsg.addMessageDef(clientOrder);
    
```
Then, you need to use the `AnyMsg` instance to encode your message.
```java 
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    Message message = new MessageImpl();
    message.setField(CLIENT_ORDER_ID, "#20190304_24392");
    message.setField(ORDER_QTY, 45);
    message.setField(ORDER_PRICE, 355.00);
    message.setField(PRODUCT_ID, "#M341_NINTENDO");
    anyMsg.encode(buffer, CLIENT_ORDER, message);
```
Finally, let's decode it from the ByteBuffer!
```java
    buffer.flip();
    Message message = anyMsg.decode(buffer);
    assertEquals("#20190304_24392", message.getField(CLIENT_ORDER_ID));
    assertEquals(45, (int)message.getField(ORDER_QTY));
    assertEquals(355.00, message.getField(ORDER_PRICE), 0.0001);
    assertEquals("#M341_NINTENDO", message.getField(PRODUCT_ID));
```