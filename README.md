Enature-SMA 
Se utiliza el algotimo de los vecisno mas cercanos (kNN) para asocier cada actibvidad a la inteligenci multiple y los estiolos de aprendizaje 

![image](https://github.com/Adrianunez8/Agentes-Jade/assets/105509664/f59d1b33-489a-4457-97ee-ad4309b7052f)


Ventajas
- Fácil de implementar: Dada la simplicidad y precisión del algoritmo, es uno de los primeros clasificadores que aprenderá un nuevo científico de datos.
- Se adapta fácilmente: A medida que se agregan nuevas muestras de entrenamiento, el algoritmo se ajusta para tener en cuenta cualquier dato nuevo, ya que todos los datos de entrenamiento se almacenan en la memoria.

- Pocos hiperparámetros: KNN solo requiere un valor k y una métrica de distancia, que es baja en comparación con otros algoritmos de machine learning.

Desventajas
- No escala bien: Dado que KNN es un algoritmo perezoso, ocupa más memoria y almacenamiento de datos en comparación con otros clasificadores. Esto puede ser costoso desde una perspectiva de tiempo y dinero. Más memoria y almacenamiento aumentarán los gastos comerciales y más datos pueden tardar más en procesarse. Si bien se han creado diferentes estructuras de datos, como Ball-Tree, para abordar las ineficiencias computacionales, un clasificador diferente puede ser ideal según el problema comercial.

-La maldición de la dimensionalidad: El algoritmo KNN tiende a ser víctima de la maldición de la dimensionalidad, lo que significa que no funciona bien con entradas de datos de alta dimensión. Esto a veces también se conoce como fenómeno de pico  (PDF, 340 MB)  (enlace externo a ibm.com), donde después de que el algoritmo alcanza la cantidad óptima de funciones, las funciones adicionales aumentan la cantidad de errores de clasificación, especialmente cuando el tamaño de la muestra es más pequeño.

-Propenso al sobreajuste: Debido a la "maldición de la dimensionalidad", KNN también es más propenso al sobreajuste. Si bien se aprovechan las técnicas de selección de características y reducción de dimensionalidad para evitar que esto ocurra, el valor de k también puede afectar el comportamiento del modelo. Los valores más bajos de k pueden sobreajustar los datos, mientras que los valores más altos de k tienden a "suavizar" los valores de predicción, ya que están promediando los valores en un área o vecindario más grande. Sin embargo, si el valor de k es demasiado alto, entonces puede ajustarse mal a los datos

Fuente:https://www.ibm.com/mx-es/topics/knn#:~:text=Pr%C3%B3ximos%20pasos-,%C2%BFQu%C3%A9%20es%20KNN%3F,un%20punto%20de%20datos%20individual. 




