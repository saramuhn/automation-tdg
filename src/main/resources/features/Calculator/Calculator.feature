@Environment:Pruebas
@Featurename:CalculatorOperations
Feature: Suma en aplicación calculadora

  @Operaciones
  Scenario Outline: <operacion> en app calculadora
    Given que quiero ejecutar una <operacion> matematica
    When cuando ingreso el <numero1> y el <numero2>
    Then la aplicacion muestra el <resultado> esperado

    @ScenarioName:Suma
    Examples:
      | operacion      | numero1 | numero2 | resultado |
      | suma           | 7       | 8       | 15        |
    @ScenarioName:Resta
    Examples:
      | operacion      | numero1 | numero2 | resultado |
      | resta          | 2       | 1       | 1         |
    @ScenarioName:Multiplicacion
    Examples:
      | operacion      | numero1 | numero2 | resultado |
      | multiplicacion | 1       | 2       | 2         |
    @ScenarioName:Division
    Examples:
      | operacion      | numero1 | numero2 | resultado |
      | division       | 4       | 2       | 2         |

