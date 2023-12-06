import { LitElement, html, css} from 'lit';
import { pages } from 'build-time-data';
import 'qwc/qwc-extension-link.js';

const NAME = "Apache Tika";
export class QwcTikaCard extends LitElement {

    static styles = css`
      .identity {
        display: flex;
        justify-content: flex-start;
      }

      .description {
        padding-bottom: 10px;
      }

      .logo {
        padding-bottom: 10px;
        margin-right: 5px;
      }

      .card-content {
        color: var(--lumo-contrast-90pct);
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        padding: 2px 2px;
        height: 100%;
      }

      .card-content slot {
        display: flex;
        flex-flow: column wrap;
        padding-top: 5px;
      }
    `;

    static properties = {
        extensionName: {attribute: true},
        description: {attribute: true},
        guide: {attribute: true},
        namespace: {attribute: true},
    };

    constructor() {
        super();
        if(!this.extensionName){
            this.extensionName = NAME;
        }
    }

    connectedCallback() {
        super.connectedCallback();
    }

    render() {
        return html`<div class="card-content" slot="content">
            <div class="identity">
                <div class="logo">
                    <img src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4KPCFET0NUWVBFIHN2ZyBQVUJMSUMgIi0vL1czQy8vRFREIFNWRyAxLjEvL0VOIiAiaHR0cDovL3d3dy53My5vcmcvR3JhcGhpY3MvU1ZHLzEuMS9EVEQvc3ZnMTEuZHRkIj4KPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB4bWw6c3BhY2U9InByZXNlcnZlIiB2aWV3Qm94PSIwIDAgMjM5Mi41IDQyMjYuNiI+CiAgPGRlZnM+CiAgICA8cGF0aCBpZD0iYiIgZmlsbD0iI0JFMjAyRSIgZD0iTTEzOTMuMiAxOTM0LjhjLTE1LjQgMi44LTMxLjMgNS41LTQ3LjYgOC4zLS4xIDAtLjIuMS0uMy4xIDguMi0xLjIgMTYuMy0yLjQgMjQuMy0zLjhzMTUuOC0yLjkgMjMuNi00LjZ6Ii8+CiAgICA8cGF0aCBpZD0iYyIgZmlsbD0iI0JFMjAyRSIgZD0iTTE0MzMuNiAxNzM1LjVzLS4xIDAtLjEuMWMtLjEgMC0uMS4xLS4yLjEgMi42LS4zIDUuMS0uOCA3LjYtMS4xIDEwLjMtMS41IDIwLjQtMy4zIDMwLjItNS40LTEyLjMgMi0yNC44IDQuMi0zNy41IDYuM3oiLz4KICAgIDxwYXRoIGlkPSJqIiBmaWxsPSIjQkUyMDJFIiBkPSJNMTM2OS42IDE5MzkuNGMtOCAxLjQtMTYuMSAyLjctMjQuMyAzLjggOC4yLTEuMSAxNi4zLTIuNCAyNC4zLTMuOHoiLz4KICAgIDxwYXRoIGlkPSJsIiBmaWxsPSIjQkUyMDJFIiBkPSJNMTQzMy4yIDE3MzUuN2MyLjYtLjMgNS4xLS44IDcuNi0xLjEtMi41LjMtNSAuNy03LjYgMS4xeiIvPgogICAgPHBhdGggaWQ9Im4iIGZpbGw9IiNCRTIwMkUiIGQ9Ik0xNDMzLjUgMTczNS42cy4xIDAgLjEtLjFjMCAwLS4xIDAtLjEuMXoiLz4KICA8L2RlZnM+CiAgPGxpbmVhckdyYWRpZW50IGlkPSJhIiB4MT0iLTUxNjcuMDk2MiIgeDI9Ii00NTcwLjExNjIiIHkxPSI2OTcuNTU0OSIgeTI9IjEzOTUuNjE5IiBncmFkaWVudFRyYW5zZm9ybT0icm90YXRlKC02NS4wMDEgLTIwNTIuOTI0Mjc4OSAtNDc3Ny44NTQyMTg5NikiIGdyYWRpZW50VW5pdHM9InVzZXJTcGFjZU9uVXNlIj4KICAgIDxzdG9wIG9mZnNldD0iMCIgc3RvcC1jb2xvcj0iI2Y2OTkyMyIvPgogICAgPHN0b3Agb2Zmc2V0PSIuMzEyMyIgc3RvcC1jb2xvcj0iI2Y3OWEyMyIvPgogICAgPHN0b3Agb2Zmc2V0PSIuODM4MyIgc3RvcC1jb2xvcj0iI2U5NzgyNiIvPgogIDwvbGluZWFyR3JhZGllbnQ+CiAgPHBhdGggZmlsbD0idXJsKCNhKSIgZD0iTTE3OTguOSAyMC4xQzE3MzIuNiA1OS4yIDE2MjIuNSAxNzAgMTQ5MSAzMzAuNWwxMjAuOCAyMjhjODQuOC0xMjEuMyAxNzAuOS0yMzAuNCAyNTcuOC0zMjMuNiA2LjctNy40IDEwLjItMTAuOSAxMC4yLTEwLjktMy40IDMuNi02LjggNy4zLTEwLjIgMTAuOS0yOC4xIDMxLTExMy40IDEzMC41LTI0Mi4xIDMyOC4xIDEyMy45LTYuMiAzMTQuMy0zMS41IDQ2OS42LTU4LjEgNDYuMi0yNTguOC00NS4zLTM3Ny4zLTQ1LjMtMzc3LjNTMTkzNS41LTYwLjYgMTc5OC45IDIwLjF6Ii8+CiAgPHBhdGggZmlsbD0ibm9uZSIgZD0iTTE1OTQuNCAxMzIwLjdjLjktLjIgMS44LS4zIDIuNy0uNWwtMTcuNCAxLjljLTEuMS41LTIgMS0zLjEgMS40IDYtLjkgMTEuOS0xLjkgMTcuOC0yLjh6bS0xMjMuMyA0MDguNGMtOS45IDIuMi0yMCAzLjktMzAuMiA1LjQgMTAuMi0xLjUgMjAuMy0zLjMgMzAuMi01LjR6bS04MzggOTE2LjFjMS4zLTMuNCAyLjYtNi44IDMuOC0xMC4yIDI2LjYtNzAuMiA1Mi45LTEzOC40IDc5LTIwNC45IDI5LjMtNzQuNiA1OC4yLTE0Ni44IDg2LjgtMjE2LjggMzAuMS03My44IDU5LjgtMTQ1LjEgODkuMS0yMTQgMzAuNy03Mi4zIDYxLTE0MS45IDkwLjctMjA4LjkgMjQuMi01NC41IDQ4LTEwNy4zIDcxLjUtMTU4LjQgNy44LTE3IDE1LjYtMzMuOSAyMy40LTUwLjYgMTUuNC0zMy4xIDMwLjctNjUuNiA0NS43LTk3LjMgMTMuOS0yOS4zIDI3LjctNTcuOSA0MS40LTg2IDQuNS05LjQgOS4xLTE4LjYgMTMuNi0yNy45LjctMS41IDEuNS0zIDIuMi00LjVsLTE0LjggMS42LTExLjgtMjMuMmMtMS4xIDIuMy0yLjMgNC41LTMuNSA2LjgtMjEuMiA0Mi4xLTQyLjIgODQuNi02MyAxMjcuNS0xMiAyNC44LTI0IDQ5LjctMzUuOSA3NC43LTMzIDY5LjMtNjUuNSAxMzkuMi05Ny40IDIwOS42LTMyLjMgNzEuMS02My45IDE0Mi42LTk0LjkgMjE0LjItMzAuNSA3MC4zLTYwLjMgMTQwLjctODkuNiAyMTAuOS0yOS4yIDcwLjEtNTcuNyAxNDAtODUuNiAyMDkuNC0yOS4xIDcyLjUtNTcuNCAxNDQuMy04NC44IDIxNS4zLTYuMiAxNi0xMi40IDMyLTE4LjUgNDgtMjIgNTcuMy00My40IDExMy44LTY0LjMgMTY5LjZsMTguNiAzNi43IDE2LjYtMS44Yy42LTEuNyAxLjItMy40IDEuOC01IDI2LjktNzMuNSA1My41LTE0NS4xIDc5LjktMjE0Ljh6bTgwMC4xLTkwOS41Yy4xIDAgLjEtLjEuMi0uMSAwIDAtLjEgMC0uMi4xeiIvPgogIDx1c2UgeGxpbms6aHJlZj0iI2IiLz4KICA8dXNlIHhsaW5rOmhyZWY9IiNiIiBvcGFjaXR5PSIuMzUiLz4KICA8dXNlIHhsaW5rOmhyZWY9IiNjIi8+CiAgPHVzZSB4bGluazpocmVmPSIjYyIgb3BhY2l0eT0iLjM1Ii8+CiAgPGxpbmVhckdyYWRpZW50IGlkPSJkIiB4MT0iLTk1ODUuMzQxOCIgeDI9Ii01MzI2LjIwOSIgeTE9IjYyMC41MDQ4IiB5Mj0iNjIwLjUwNDgiIGdyYWRpZW50VHJhbnNmb3JtPSJyb3RhdGUoLTY1LjAwMSAtMjA1Mi45MjQyNzg5IC00Nzc3Ljg1NDIxODk2KSIgZ3JhZGllbnRVbml0cz0idXNlclNwYWNlT25Vc2UiPgogICAgPHN0b3Agb2Zmc2V0PSIuMzIzMyIgc3RvcC1jb2xvcj0iIzllMjA2NCIvPgogICAgPHN0b3Agb2Zmc2V0PSIuNjMwMiIgc3RvcC1jb2xvcj0iI2M5MjAzNyIvPgogICAgPHN0b3Agb2Zmc2V0PSIuNzUxNCIgc3RvcC1jb2xvcj0iI2NkMjMzNSIvPgogICAgPHN0b3Agb2Zmc2V0PSIxIiBzdG9wLWNvbG9yPSIjZTk3ODI2Ii8+CiAgPC9saW5lYXJHcmFkaWVudD4KICA8cGF0aCBmaWxsPSJ1cmwoI2QpIiBkPSJNMTI1NS43IDExNDcuNmMzNi43LTY4LjYgNzMuOS0xMzUuNyAxMTEuNS0yMDEgMzktNjcuOCA3OC41LTEzMy42IDExOC40LTE5NyAyLjMtMy43IDQuNy03LjUgNy0xMS4zIDM5LjQtNjIuNCA3OS4yLTEyMi40IDExOS4zLTE3OS44bC0xMjAuOC0yMjhjLTkuMSAxMS4xLTE4LjIgMjIuNC0yNy41IDMzLjktMzQuOCA0My40LTcxIDkwLjEtMTA4LjEgMTM5LjYtNDEuOCA1NS44LTg0LjggMTE1LjQtMTI4LjUgMTc3LjktNDAuMyA1Ny44LTgxLjIgMTE4LjMtMTIyLjEgMTgwLjktMzQuOCA1My4zLTY5LjggMTA4LjItMTA0LjUgMTY0LjVsLTMuOSA2LjMgMTU3LjIgMzEwLjVjMzMuNi02Ni41IDY3LjYtMTMyLjEgMTAyLTE5Ni41eiIvPgogIDxsaW5lYXJHcmFkaWVudCBpZD0iZSIgeDE9Ii05MDcxLjIwNyIgeDI9Ii02NTMzLjE3ODIiIHkxPSIxMDQ3LjY4OTgiIHkyPSIxMDQ3LjY4OTgiIGdyYWRpZW50VHJhbnNmb3JtPSJyb3RhdGUoLTY1LjAwMSAtMjA1Mi45MjQyNzg5IC00Nzc3Ljg1NDIxODk2KSIgZ3JhZGllbnRVbml0cz0idXNlclNwYWNlT25Vc2UiPgogICAgPHN0b3Agb2Zmc2V0PSIwIiBzdG9wLWNvbG9yPSIjMjgyNjYyIi8+CiAgICA8c3RvcCBvZmZzZXQ9Ii4wOTU0ODM5IiBzdG9wLWNvbG9yPSIjNjYyZThkIi8+CiAgICA8c3RvcCBvZmZzZXQ9Ii43ODgyIiBzdG9wLWNvbG9yPSIjOWYyMDY0Ii8+CiAgICA8c3RvcCBvZmZzZXQ9Ii45NDg3IiBzdG9wLWNvbG9yPSIjY2QyMDMyIi8+CiAgPC9saW5lYXJHcmFkaWVudD4KICA8cGF0aCBmaWxsPSJ1cmwoI2UpIiBkPSJNNTM5LjcgMjg5Ny4xYy0yMC44IDU3LjItNDEuNyAxMTUuNC02Mi43IDE3NC45LS4zLjktLjYgMS43LS45IDIuNi0zIDguNC01LjkgMTYuOC04LjkgMjUuMi0xNC4xIDQwLjEtMjYuNCA3Ni4yLTU0LjUgMTU4LjMgNDYuMyAyMS4xIDgzLjUgNzYuNyAxMTguNyAxMzkuOC0zLjctNjUuMy0zMC44LTEyNi43LTgyLjEtMTc0LjIgMjI4LjMgMTAuMyA0MjUtNDcuNCA1MjYuNy0yMTQuMyA5LjEtMTQuOSAxNy40LTMwLjUgMjQuOS00Ny4yLTQ2LjIgNTguNi0xMDMuNSA4My41LTIxMS40IDc3LjQtLjIuMS0uNS4yLS43LjMuMi0uMS41LS4yLjctLjMgMTU4LjgtNzEuMSAyMzguNS0xMzkuMyAzMDguOS0yNTIuNCAxNi43LTI2LjggMzIuOS01Ni4xIDQ5LjUtODguNi0xMzguOSAxNDIuNi0yOTkuOCAxODMuMi00NjkuMyAxNTIuNGwtMTI3LjEgMTMuOWMtNCAxMC43LTcuOSAyMS40LTExLjggMzIuMnoiLz4KICA8bGluZWFyR3JhZGllbnQgaWQ9ImYiIHgxPSItOTM0Ni4xMjYiIHgyPSItNTA4Ni45OTQxIiB5MT0iNTgwLjgxNyIgeTI9IjU4MC44MTciIGdyYWRpZW50VHJhbnNmb3JtPSJyb3RhdGUoLTY1LjAwMSAtMjA1Mi45MjQyNzg5IC00Nzc3Ljg1NDIxODk2KSIgZ3JhZGllbnRVbml0cz0idXNlclNwYWNlT25Vc2UiPgogICAgPHN0b3Agb2Zmc2V0PSIuMzIzMyIgc3RvcC1jb2xvcj0iIzllMjA2NCIvPgogICAgPHN0b3Agb2Zmc2V0PSIuNjMwMiIgc3RvcC1jb2xvcj0iI2M5MjAzNyIvPgogICAgPHN0b3Agb2Zmc2V0PSIuNzUxNCIgc3RvcC1jb2xvcj0iI2NkMjMzNSIvPgogICAgPHN0b3Agb2Zmc2V0PSIxIiBzdG9wLWNvbG9yPSIjZTk3ODI2Ii8+CiAgPC9saW5lYXJHcmFkaWVudD4KICA8cGF0aCBmaWxsPSJ1cmwoI2YpIiBkPSJNNTk5IDI2MTIuNGMyNy41LTcxIDU1LjgtMTQyLjggODQuOC0yMTUuMyAyNy44LTY5LjQgNTYuNC0xMzkuMiA4NS42LTIwOS40IDI5LjItNzAuMiA1OS4xLTE0MC41IDg5LjYtMjEwLjkgMzEtNzEuNiA2Mi43LTE0My4xIDk0LjktMjE0LjIgMzEuOS03MC4zIDY0LjQtMTQwLjMgOTcuNC0yMDkuNiAxMS45LTI1IDIzLjktNDkuOSAzNS45LTc0LjcgMjAuOC00Mi45IDQxLjgtODUuNCA2My0xMjcuNSAxLjEtMi4zIDIuMy00LjUgMy41LTYuOGwtMTU3LjItMzEwLjVjLTIuNiA0LjItNS4xIDguNC03LjcgMTIuNi0zNi42IDU5LjgtNzMuMSAxMjEtMTA4LjkgMTgzLjUtMzYuMiA2My4xLTcxLjcgMTI3LjQtMTA2LjQgMTkyLjYtMjkuMyA1NS01Ny45IDExMC41LTg1LjcgMTY2LjUtNS42IDExLjQtMTEuMSAyMi42LTE2LjYgMzMuOS0zNC4zIDcwLjUtNjUuMiAxMzguNi05My4yIDIwNC4xLTMxLjcgNzQuMi01OS42IDE0NS4xLTg0IDIxMi4zLTE2LjEgNDQuMi0zMC43IDg2LjktNDQuMSAxMjcuOS0xMSAzNS0yMS41IDcwLjEtMzEuNCAxMDUtMjMuNSA4Mi4zLTQzLjcgMTY0LjQtNjAuMyAyNDYuMmwxNTggMzExLjljMjAuOS01NS44IDQyLjMtMTEyLjMgNjQuMy0xNjkuNiA2LjEtMTUuOSAxMi4zLTMyIDE4LjUtNDh6Ii8+CiAgPGxpbmVhckdyYWRpZW50IGlkPSJnIiB4MT0iLTkwMzUuNTAyOSIgeDI9Ii02Nzk3LjIwMTIiIHkxPSI2MzguNDQwOCIgeTI9IjYzOC40NDA4IiBncmFkaWVudFRyYW5zZm9ybT0icm90YXRlKC02NS4wMDEgLTIwNTIuOTI0Mjc4OSAtNDc3Ny44NTQyMTg5NikiIGdyYWRpZW50VW5pdHM9InVzZXJTcGFjZU9uVXNlIj4KICAgIDxzdG9wIG9mZnNldD0iMCIgc3RvcC1jb2xvcj0iIzI4MjY2MiIvPgogICAgPHN0b3Agb2Zmc2V0PSIuMDk1NDgzOSIgc3RvcC1jb2xvcj0iIzY2MmU4ZCIvPgogICAgPHN0b3Agb2Zmc2V0PSIuNzg4MiIgc3RvcC1jb2xvcj0iIzlmMjA2NCIvPgogICAgPHN0b3Agb2Zmc2V0PSIuOTQ4NyIgc3RvcC1jb2xvcj0iI2NkMjAzMiIvPgogIDwvbGluZWFyR3JhZGllbnQ+CiAgPHBhdGggZmlsbD0idXJsKCNnKSIgZD0iTTM1Ni4xIDI1MjkuMmMtMTkuOCA5OS44LTMzLjkgMTk5LjItNDEgMjk4LS4yIDMuNS0uNiA2LjktLjggMTAuNC00OS4zLTc5LTE4MS4zLTE1Ni4xLTE4MS0xNTUuNCA5NC41IDEzNyAxNjYuMiAyNzMgMTc2LjkgNDA2LjUtNTAuNiAxMC40LTExOS45LTQuNi0yMDAtMzQuMSA4My41IDc2LjcgMTQ2LjIgOTcuOSAxNzAuNiAxMDMuNi03Ni43IDQuOC0xNTYuNiA1Ny41LTIzNy4xIDExOC4yIDExNy43LTQ4IDIxMi44LTY3IDI4MC45LTUxLjYtMTA4IDMwNS44LTIxNi4zIDY0My40LTMyNC42IDEwMDEuOCAzMy4yLTkuOCA1My0zMi4xIDY0LjEtNjIuMyAxOS4zLTY0LjkgMTQ3LjQtNDkwLjcgMzQ4LjEtMTA1MC40IDUuNy0xNS45IDExLjUtMzEuOSAxNy4zLTQ4IDEuNi00LjUgMy4zLTkgNC45LTEzLjQgMjEuMi01OC43IDQzLjItMTE4LjYgNjUuOS0xNzkuNyA1LjItMTMuOSAxMC40LTI3LjggMTUuNi00MS44LjEtLjMuMi0uNi4zLS44bC0xNTcuOC0zMTEuOGMtLjcgMy41LTEuNiA3LjEtMi4zIDEwLjh6Ii8+CiAgPGxpbmVhckdyYWRpZW50IGlkPSJoIiB4MT0iLTkzNDYuMTI2IiB4Mj0iLTUwODYuOTk0MSIgeTE9IjEwMjEuNjIxOCIgeTI9IjEwMjEuNjIxOCIgZ3JhZGllbnRUcmFuc2Zvcm09InJvdGF0ZSgtNjUuMDAxIC0yMDUyLjkyNDI3ODkgLTQ3NzcuODU0MjE4OTYpIiBncmFkaWVudFVuaXRzPSJ1c2VyU3BhY2VPblVzZSI+CiAgICA8c3RvcCBvZmZzZXQ9Ii4zMjMzIiBzdG9wLWNvbG9yPSIjOWUyMDY0Ii8+CiAgICA8c3RvcCBvZmZzZXQ9Ii42MzAyIiBzdG9wLWNvbG9yPSIjYzkyMDM3Ii8+CiAgICA8c3RvcCBvZmZzZXQ9Ii43NTE0IiBzdG9wLWNvbG9yPSIjY2QyMzM1Ii8+CiAgICA8c3RvcCBvZmZzZXQ9IjEiIHN0b3AtY29sb3I9IiNlOTc4MjYiLz4KICA8L2xpbmVhckdyYWRpZW50PgogIDxwYXRoIGZpbGw9InVybCgjaCkiIGQ9Ik0xMTc4LjEgMTM3MC4zYy00LjUgOS4yLTkgMTguNS0xMy42IDI3LjktMTMuNiAyOC4xLTI3LjQgNTYuNy00MS40IDg2LTE1LjEgMzEuNy0zMC4zIDY0LjEtNDUuNyA5Ny4zLTcuOCAxNi43LTE1LjUgMzMuNS0yMy40IDUwLjYtMjMuNSA1MS4xLTQ3LjMgMTAzLjktNzEuNSAxNTguNC0yOS43IDY3LTYwIDEzNi42LTkwLjcgMjA4LjktMjkuMyA2OC45LTU5IDE0MC4yLTg5LjEgMjE0LTI4LjYgNzAtNTcuNSAxNDIuMy04Ni44IDIxNi44LTI2LjEgNjYuNS01Mi40IDEzNC43LTc5IDIwNC45LTEuMyAzLjQtMi42IDYuOC0zLjggMTAuMi0yNi40IDY5LjctNTMgMTQxLjMtNzkuOCAyMTQuNy0uNiAxLjctMS4yIDMuNC0xLjggNWwxMjcuMS0xMy45Yy0yLjUtLjUtNS4xLS44LTcuNi0xLjMgMTUyLTE4LjkgMzU0LTEzMi41IDQ4NC42LTI3Mi43IDYwLjItNjQuNiAxMTQuOC0xNDAuOCAxNjUuMy0yMzAgMzcuNi02Ni40IDcyLjktMTQwIDEwNi41LTIyMS41IDI5LjQtNzEuMiA1Ny42LTE0OC4zIDg0LjgtMjMxLjktMzQuOSAxOC40LTc0LjkgMzEuOS0xMTkgNDEuMy03LjcgMS42LTE1LjYgMy4yLTIzLjYgNC42LTggMS40LTE2LjEgMi43LTI0LjMgMy44LjEgMCAuMi0uMS4zLS4xIDE0MS43LTU0LjUgMjMxLjEtMTU5LjggMjk2LjEtMjg4LjctMzcuMyAyNS40LTk3LjkgNTguNy0xNzAuNSA3NC43LTkuOSAyLjItMjAgMy45LTMwLjIgNS40LTIuNi40LTUuMS44LTcuNiAxLjEuMSAwIC4xLS4xLjItLjEgMCAwIC4xIDAgLjEtLjEgNDkuMi0yMC42IDkwLjctNDMuNiAxMjYuNy03MC44IDcuNy01LjggMTUuMi0xMS44IDIyLjQtMTguMSAxMS05LjUgMjEuNC0xOS41IDMxLjQtMzAgNi40LTYuNyAxMi42LTEzLjYgMTguNi0yMC44IDE0LjEtMTYuOCAyNy4zLTM0LjkgMzkuNy01NC42IDMuOC02IDcuNS0xMi4xIDExLjItMTguNCA0LjctOS4xIDkuMi0xOCAxMy42LTI2LjggMTkuOC0zOS44IDM1LjYtNzUuMyA0OC4yLTEwNi41IDYuMy0xNS42IDExLjgtMzAgMTYuNS00My40IDEuOS01LjMgMy43LTEwLjUgNS40LTE1LjUgNS0xNSA5LjEtMjguMyAxMi4zLTQwIDQuOC0xNy41IDcuNy0zMS40IDkuMy00MS41LTQuOCAzLjgtMTAuMyA3LjYtMTYuNSAxMS4zLTQyLjggMjUuNi0xMTYuMiA0OC44LTE3NS40IDU5LjdsMTE2LjctMTIuOC0xMTYuNyAxMi44Yy0uOS4yLTEuOC4zLTIuNy41LTUuOSAxLTExLjkgMS45LTE3LjkgMi45IDEuMS0uNSAyLTEgMy4xLTEuNGwtMzk5LjMgNDMuOGMtLjcgMS40LTEuNCAyLjgtMi4yIDQuM3oiLz4KICA8bGluZWFyR3JhZGllbnQgaWQ9ImkiIHgxPSItOTYxMC4zMzQiIHgyPSItNTM1MS4yMDE3IiB5MT0iOTk5LjczMyIgeTI9Ijk5OS43MzMiIGdyYWRpZW50VHJhbnNmb3JtPSJyb3RhdGUoLTY1LjAwMSAtMjA1Mi45MjQyNzg5IC00Nzc3Ljg1NDIxODk2KSIgZ3JhZGllbnRVbml0cz0idXNlclNwYWNlT25Vc2UiPgogICAgPHN0b3Agb2Zmc2V0PSIuMzIzMyIgc3RvcC1jb2xvcj0iIzllMjA2NCIvPgogICAgPHN0b3Agb2Zmc2V0PSIuNjMwMiIgc3RvcC1jb2xvcj0iI2M5MjAzNyIvPgogICAgPHN0b3Agb2Zmc2V0PSIuNzUxNCIgc3RvcC1jb2xvcj0iI2NkMjMzNSIvPgogICAgPHN0b3Agb2Zmc2V0PSIxIiBzdG9wLWNvbG9yPSIjZTk3ODI2Ii8+CiAgPC9saW5lYXJHcmFkaWVudD4KICA8cGF0aCBmaWxsPSJ1cmwoI2kpIiBkPSJNMTYyNy42IDU2My4xYy0zNS41IDU0LjUtNzQuMyAxMTYuNC0xMTYgMTg2LjUtMi4yIDMuNi00LjQgNy40LTYuNiAxMS4xLTM2IDYwLjctNzQuMyAxMjcuMy0xMTQuNSAyMDAuMy0zNC44IDYzLTcxIDEzMC42LTEwOC42IDIwMy4zLTMyLjggNjMuMy02Ni43IDEzMC41LTEwMS41IDIwMS42bDM5OS4zLTQzLjhjMTE2LjMtNTMuNSAxNjguMy0xMDEuOSAyMTguOC0xNzEuOSAxMy40LTE5LjMgMjYuOS0zOS41IDQwLjMtNjAuNCA0MS02NCA4MS4yLTEzNC41IDExNy4yLTIwNC42IDM0LjctNjcuNyA2NS4zLTEzNC44IDg4LjgtMTk1LjMgMTQuOS0zOC41IDI2LjktNzQuMyAzNS4yLTEwNS43IDcuMy0yNy43IDEzLTU0IDE3LjQtNzkuMS0xNTUuNSAyNi41LTM0NS45IDUxLjktNDY5LjggNTh6Ii8+CiAgPHVzZSB4bGluazpocmVmPSIjaiIvPgogIDx1c2UgeGxpbms6aHJlZj0iI2oiIG9wYWNpdHk9Ii4zNSIvPgogIDxsaW5lYXJHcmFkaWVudCBpZD0iayIgeDE9Ii05MzQ2LjEyNiIgeDI9Ii01MDg2Ljk5NDEiIHkxPSIxMTUyLjcyNjEiIHkyPSIxMTUyLjcyNjEiIGdyYWRpZW50VHJhbnNmb3JtPSJyb3RhdGUoLTY1LjAwMSAtMjA1Mi45MjQyNzg5IC00Nzc3Ljg1NDIxODk2KSIgZ3JhZGllbnRVbml0cz0idXNlclNwYWNlT25Vc2UiPgogICAgPHN0b3Agb2Zmc2V0PSIuMzIzMyIgc3RvcC1jb2xvcj0iIzllMjA2NCIvPgogICAgPHN0b3Agb2Zmc2V0PSIuNjMwMiIgc3RvcC1jb2xvcj0iI2M5MjAzNyIvPgogICAgPHN0b3Agb2Zmc2V0PSIuNzUxNCIgc3RvcC1jb2xvcj0iI2NkMjMzNSIvPgogICAgPHN0b3Agb2Zmc2V0PSIxIiBzdG9wLWNvbG9yPSIjZTk3ODI2Ii8+CiAgPC9saW5lYXJHcmFkaWVudD4KICA8cGF0aCBmaWxsPSJ1cmwoI2spIiBkPSJNMTM2OS42IDE5MzkuNGMtOCAxLjQtMTYuMSAyLjctMjQuMyAzLjggOC4yLTEuMSAxNi4zLTIuNCAyNC4zLTMuOHoiLz4KICA8dXNlIHhsaW5rOmhyZWY9IiNsIi8+CiAgPHVzZSB4bGluazpocmVmPSIjbCIgb3BhY2l0eT0iLjM1Ii8+CiAgPGxpbmVhckdyYWRpZW50IGlkPSJtIiB4MT0iLTkzNDYuMTI2IiB4Mj0iLTUwODYuOTk0MSIgeTE9IjExMzcuNzI0NyIgeTI9IjExMzcuNzI0NyIgZ3JhZGllbnRUcmFuc2Zvcm09InJvdGF0ZSgtNjUuMDAxIC0yMDUyLjkyNDI3ODkgLTQ3NzcuODU0MjE4OTYpIiBncmFkaWVudFVuaXRzPSJ1c2VyU3BhY2VPblVzZSI+CiAgICA8c3RvcCBvZmZzZXQ9Ii4zMjMzIiBzdG9wLWNvbG9yPSIjOWUyMDY0Ii8+CiAgICA8c3RvcCBvZmZzZXQ9Ii42MzAyIiBzdG9wLWNvbG9yPSIjYzkyMDM3Ii8+CiAgICA8c3RvcCBvZmZzZXQ9Ii43NTE0IiBzdG9wLWNvbG9yPSIjY2QyMzM1Ii8+CiAgICA8c3RvcCBvZmZzZXQ9IjEiIHN0b3AtY29sb3I9IiNlOTc4MjYiLz4KICA8L2xpbmVhckdyYWRpZW50PgogIDxwYXRoIGZpbGw9InVybCgjbSkiIGQ9Ik0xNDMzLjIgMTczNS43YzIuNi0uMyA1LjEtLjggNy42LTEuMS0yLjUuMy01IC43LTcuNiAxLjF6Ii8+CiAgPHVzZSB4bGluazpocmVmPSIjbiIvPgogIDx1c2UgeGxpbms6aHJlZj0iI24iIG9wYWNpdHk9Ii4zNSIvPgogIDxsaW5lYXJHcmFkaWVudCBpZD0ibyIgeDE9Ii02OTUzLjQwNzIiIHgyPSItNjAxMS45OTk1IiB5MT0iMTEzNC43MTYxIiB5Mj0iMTEzNC43MTYxIiBncmFkaWVudFRyYW5zZm9ybT0icm90YXRlKC02NS4wMDEgLTIwNTIuOTI0Mjc4OSAtNDc3Ny44NTQyMTg5NikiIGdyYWRpZW50VW5pdHM9InVzZXJTcGFjZU9uVXNlIj4KICAgIDxzdG9wIG9mZnNldD0iLjMyMzMiIHN0b3AtY29sb3I9IiM5ZTIwNjQiLz4KICAgIDxzdG9wIG9mZnNldD0iLjYzMDIiIHN0b3AtY29sb3I9IiNjOTIwMzciLz4KICAgIDxzdG9wIG9mZnNldD0iLjc1MTQiIHN0b3AtY29sb3I9IiNjZDIzMzUiLz4KICAgIDxzdG9wIG9mZnNldD0iMSIgc3RvcC1jb2xvcj0iI2U5NzgyNiIvPgogIDwvbGluZWFyR3JhZGllbnQ+CiAgPHBhdGggZmlsbD0idXJsKCNvKSIgZD0iTTE0MzMuNSAxNzM1LjZzLjEgMCAuMS0uMWMwIDAtLjEgMC0uMS4xeiIvPgogIDxwYXRoIGZpbGw9IiM2RDZFNzEiIGQ9Ik0yMjE4LjcgMzg3djExLjdoMjcuNHY3Ny40aDEyLjd2LTc3LjRoMjcuNlYzODdoLTY3Ljd6bTE1OC4zIDAtMzAuNiA2Mi4yLTMwLjgtNjIuMmgtMTUuM3Y4OS4xaDExLjd2LTcwLjdsMzAuMyA2MS4zaDguMWwzMC4zLTYxLjN2NzAuN2gxMS43VjM4N0gyMzc3eiIvPgo8L3N2Zz4="
                                       alt="${NAME}" 
                                       title="${NAME}"
                                       width="32" 
                                       height="32">
                </div>
                <div class="description">${this.description}</div>
            </div>
            ${this._renderCardLinks()}
        </div>
        `;
    }

    _renderCardLinks(){
        return html`${pages.map(page => html`
                            <qwc-extension-link slot="link"
                                namespace="${this.namespace}"
                                extensionName="${this.extensionName}"
                                iconName="${page.icon}"
                                displayName="${page.title}"
                                staticLabel="${page.staticLabel}"
                                dynamicLabel="${page.dynamicLabel}"
                                streamingLabel="${page.streamingLabel}"
                                path="${page.id}"
                                ?embed=${page.embed}
                                externalUrl="${page.metadata.externalUrl}"
                                webcomponent="${page.componentLink}" >
                            </qwc-extension-link>
                        `)}`;
    }

}
customElements.define('qwc-tika-card', QwcTikaCard);