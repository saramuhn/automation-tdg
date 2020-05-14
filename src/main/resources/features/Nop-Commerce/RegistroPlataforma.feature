@Environment:Pruebas
@FeatureName:RegistroPlataforma
  Feature:
    HU002 – Registro en la plataforma de un cliente
    Yo como comprador de la tienda
    Quiero poder registrarme a la plataforma
    Para realizar comprar a través de internet.

  @ScenarioName:Register
  Scenario: Registro de un cliente en la plataforma
    Given que quiero registrarme en la aplicacion en la opción de Register
    When ingreso la información de Registro y valido la informacion
    Then permite registrarme mostrando un mensaje de  usuario creado
