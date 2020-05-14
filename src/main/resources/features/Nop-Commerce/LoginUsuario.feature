@Environment:Pruebas
@FeatureName:LoginUsuario
  Feature:
    HU003 – Login de Usuario
    Yo como comprador de la tienda
    Quiero poder loguearme a la plataforma
    Para realizar comprar a través de internet.

  Scenario Outline: Opciones de login
    Given que quiero autenticarme en la aplicacion
    And ingrese en la opcion de Log in desde el home
    When ingrese los datos "<correspondientes>" para loguearse
    Then muestra "<mensaje>" en la aplicacion
    Examples:
    |correspondientes|mensaje|
    |validos         |Welcome to our store|
    |invalidos       |Login was unsuccessful. Please correct the errors and try again.  No customer account found|


