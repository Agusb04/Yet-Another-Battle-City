# 🎮 TP1: YABC — *Yet Another Battle City*

---

## 🎓 Información Académica

* **Universidad:** Universidad de Buenos Aires
* **Facultad:** Facultad de Ingeniería
* **Materia:** Paradigmas de Programación (TB025)

---

## 👨‍🏫 Equipo Docente

* **Docente a cargo:** Diego Essaya
* **Docente corrector:** Diego Essaya
* **JTP:** Santiago Maraggi

---

## 👨‍💻 Autores

* Valentino Garcia Vázquez
* Agustín Beltrame Labanca

**Grupo:** SSJ — *Siempre Sufriendo con Java*

---

# 🕹️ Descripción del Proyecto

**YABC (Yet Another Battle City)** es una recreación del clásico arcade *Battle City*, desarrollada en **Java** utilizando **JavaFX** para la interfaz gráfica, animaciones y gestión de eventos.

El proyecto implementa una arquitectura basada en el patrón **MVC (Model–View–Controller)**, separando claramente:

* **Modelo:** lógica del juego y entidades del dominio
* **Vista:** renderizado gráfico e interfaz visual
* **Controlador:** gestión de inputs, eventos y flujo del juego

Además, el sistema de sonidos fue desacoplado en un módulo independiente para mejorar la organización y mantenibilidad del proyecto.

---

# 🚀 Características Principales

## 🎮 Modos de Juego

* Modo **single player**
* Modo **cooperativo local (multiplayer)**

---

## 💥 Power-Ups

* **Granada:** elimina todos los enemigos en pantalla
* **Casco:** otorga invulnerabilidad temporal
* **Estrella:** permite destruir enemigos de un solo disparo

---

## 🤖 Tipos de Enemigos

* **Básico (marrón):** lento y resistente estándar
* **Veloz (azul):** mayor velocidad de movimiento
* **Potente (rojo):** mayor frecuencia de disparo
* **Blindado (naranja):** requiere múltiples impactos

---

## 🌍 Gameplay

* Sistema de **spawn dinámico de enemigos**
* **3 niveles** para cada modo de juego
* Sistema de **colisiones**
* Interacciones entre bloques, disparos y tanques
* Animaciones y renderizado en tiempo real
* Gestión independiente de sonidos y efectos

---

## ⚠️ Mecánicas Especiales

* Destrucción de la base principal (*game over*)
* Fuego amigo entre jugadores
* Congelamiento temporal al recibir disparos aliados

---

# 🏗️ Arquitectura del Proyecto

El proyecto fue reorganizado siguiendo el patrón **MVC**:

## 📦 Modelo (`modelos/`)

Contiene toda la lógica del dominio:

* Tanques
* Disparos
* Power-ups
* Bloques
* Niveles
* Movimiento y colisiones

---

## 🎨 Vista (`vista/`)

Responsable de:

* Renderizado gráfico
* Animaciones
* HUD e interfaz
* Sprites y representación visual de entidades

---

## 🎮 Controladores (`controlador/`)

Gestionan:

* Inputs del jugador
* Flujo principal del juego
* Coordinación entre modelo y vista
* Eventos y lógica de interacción

---

## 🔊 Sonidos (`sonidos/`)

Módulo desacoplado encargado de:

* Reproducción de efectos de sonido
* Gestión de audio del juego
* Centralización de recursos sonoros

---

# ⚙️ Requisitos

* **Java 21**
* **Apache Maven**
* **JavaFX 21.0.6**

---

# ▶️ Ejecución

```bash
git clone https://github.com/paradigmas-tb025-essaya/tp1-ssj.git
cd TP1_YABC
mvn clean javafx:run
```

---

# 🧩 Estructura del Proyecto

```text
modelos/      → lógica del dominio
vista/        → renderizado e interfaz gráfica
controlador/  → controladores y manejo de eventos
sonidos/      → sistema de audio
resources/    → assets, sprites y niveles
test/         → pruebas unitarias e integración
```

---

# 🧪 Testing

El proyecto incluye:

## ✅ Tests Unitarios

Cobertura sobre:

* Bloques
* Disparos
* Power-ups
* Enemigos
* Jugadores

## ✅ Test de Integración

Validación del comportamiento general del juego y sus interacciones principales.

---

# Conceptos Aplicados

Este trabajo práctico pone en práctica conceptos fundamentales de:

* Programación Orientada a Objetos
* Patrón MVC
* Separación de responsabilidades
* Modelado de dominio
* Testing automatizado
* JavaFX y renderizado gráfico
* Arquitectura modular

---

#  Objetivo Académico

El proyecto busca recrear un videojuego clásico aplicando buenas prácticas de diseño de software, arquitectura y testing dentro del paradigma orientado a objetos utilizando Java.

---
