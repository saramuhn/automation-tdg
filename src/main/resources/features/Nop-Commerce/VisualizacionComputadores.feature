@Environment:Pruebas
@FeatureName:SeccionDeComputadores
Feature: Pruebas a demo de compras online
  HU001 – Sección de Computadores
  Yo como comprador de la tienda
  Quiero poder realizar la visualización de computadores que venden en un sitio web
  Para no tener que ir a la tienda física

  @First
    #Escenario 1
    @ScenarioName:VisualizarCategorias
  Scenario Outline: Visualizar categorias de la opcion Computers
    Given que quiero comprar online en la pagina de NopCommerce
    When cliqueo la opcion Computers del menu
    Then muestra "<categoria disponible>"
    And muestra la informacion con los productos de la "<categoria disponible>"

    Examples:
      | categoria disponible |
      | Desktops             |
      | Notebooks            |
      | Software             |

#Escenario 2
  @ScenarioName:FiltroPorPrecio
  Scenario Outline: Filtrar por "<categoria disponible>" los precios de los productos
    Given que quiero filtrar el precio de la "<categoria disponible>"
    When cliqueo el "<filtro>"
    Then muestra productos en el rango del "<filtro>" en la  "<categoria disponible>"
    And el resumen del producto
    Examples:
      | categoria disponible | filtro    |
      | Desktops             | -1000     |
      | Desktops             | 1000-1200 |
      | Desktops             | 1200-     |
      | Notebooks            | -1000     |
      | Notebooks            | 1000-1200 |
      | Notebooks            | 1200-     |

    #Escenario 3

  @ScenarioName:AddToCart
  Scenario Outline: Añadir prodcuctos al carrito de compras o lista de deseos
    Given que busque un producto en la "<categoria>"
    When cliqueo el producto agregando una "<cantidad>" para agregarlo a "<medio>"
    Then muestra el detalle del producto con el precio de la "<cantidad>" con la informacion del "<medio>"

    Examples:
      | categoria | medio       | cantidad |
      | Desktops  | wishlist    | 1        |
      | Desktops  | add to cart | 3        |
      | Notebooks | wishlist    | 1        |
      | Notebooks | add to cart | 3        |
      | Software  | wishlist    | 1        |
      | Software  | add to cart | 3        |


