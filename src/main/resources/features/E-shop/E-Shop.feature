 #Estos tags proporcionan información durante la ejecución sobre el feature y ambiente que se está ejecutando
#Este parametro se puede obtener desde el step con testinfo.getEnvironment()
 @Environment:Pruebas
#Este parametro se puede obtener desde el step con testinfo.getFeatureName()
@FeatureName:E-shopTests
Feature: Pruebas portal de compras online


  #ESCENARIO 1

  @Autenticacion
  Scenario: Autenticacion en e-shop
    Given que quiero autenticarme en el portal de compras
    When ingreso mis credenciales de acceso
    Then ingreso a mi cuenta


    #ESCENARIO 2

   #Estos tags se pueden obtener desde el step con testinfo.getScenarioTags()
  #Clasificamos por tags los escenarios para que podamos independizar y personalizar su ejecución posteriormente desde el runner
  @Busqueda   @CalculoTotal
    #Este valor del Scenario Outline se puede obtener desde el step con testinfo.getScenarioValue()
  Scenario Outline: Busqueda y calculo del precio total de <cantidad> <producto> en el carrito para <MetodoAcceso>
    # En estos pasos podemos encontrar una aplicación de las herramientas de automatización de una interfaz web en el framework
    Given ingreso al portal para comprar en la e-shop para <MetodoAcceso>
    When ingreso el <producto> para buscar por <MetodoAcceso>
    And verifico que el <producto> exista para su eleccion
    # En este paso podemos encontrar una aplicación de las herramientas de manejo de base de datos en el framework
    And el <producto> cuente con el precio unitario que registra en la base de datos
    And agrego <cantidad> de unidades por <MetodoAgregado> al carrito
    Then entonces el detalle de la compra muestra el total calculado
    # En este paso podemos encontrar una aplicación de las herramientas de consumo de Web Services en el framework
    And es aproximado al mostrado por el <WebService> para la <cantidad>

    #Se puede usar esta estructura si es necesario enviar información desde el feature a la ejecución para
    # agrupaciones de escenarios que requieren información explicita (Por ej: Si se desea tener una carpeta de evidencia independiente para cada escenario)
    # Si no es necesario que los escenarios tengan información explicita se pueden usar los examples agrupados como
    # comunmente se usan

    #Este parametro se puede obtener desde el step con testinfo.getCustomParam("MyCustom") solo para este example
    @MyCustom:Parameter
    @ScenarioName:BuscadorPorTexto
    Examples:
      | MetodoAcceso              | producto                    | cantidad | MetodoAgregado | WebService  |
      | Buscador pagina principal | Faded Short Sleeve T-shirts | 5        | texto          | Calculadora |
    #Este parametro se puede obtener desde el step con testinfo.getScenarioName()
    @ScenarioName:RecomendadosPorBoton
    Examples:
      | MetodoAcceso                  | producto              | cantidad | MetodoAgregado | WebService  |
      | Recomendados pagina principal | Printed Chiffon Dress | 2        | boton          | Calculadora |
