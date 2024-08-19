# Assembly Odin
#### School Management System for Point-Based Learning
[![GitHub repo size](https://img.shields.io/github/repo-size/Rafael4DC/AssemblyOdin.svg?logo=github&style=social)](https://github.com/Rafael4DC/AssemblyOdin) [![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/Rafael4DC/AssemblyOdin.svg?logo=git&style=social)](https://github.com/Rafael4DC/AssemblyOdin) [![GitHub license](https://img.shields.io/github/license/Rafael4DC/AssemblyOdin.svg?style=social&logo=github)](https://github.com/Rafael4DC/AssemblyOdin/blob/main/LICENSE)

## What is Assembly

The Assembly school in Lisbon positions itself as a pioneer technology education institution aiming to prepare future professionals in Portugal for a technology-driven world. Recognizing the limitations of traditional education in adapting to the rapidly evolving technological landscape, Assembly offers a unique learning methodology, especially aimed at teaching children and adolescents. This approach allows students to tailor their education according to their interests and strengths, focusing on essential tech skills like coding, robotics, and design.

## Problematic
However, the current system for managing student activities presents several chal-
lenges that are increasingly becoming untenable for a growing institution like Assem-
bly. Teachers have to manually mark students’ attendance and activities on Excel sheets,
which are then allocate points to students. Students subsequently record their gaming
times on sheets of paper when requesting gaming equipment, and administrators must
then transfer these records to a different Excel sheet. This manual process is not only in-
efficient and prone to errors, but it is also difficult to manage, lacks scalability and doesn’t
fit the idea of what Assembly is meant to be. As Assembly continues to expand, the limita-
tions of this outdated system become more pronounced, underscoring the urgent need for
a scalable, automated solution that can efficiently handle the institution’s growing needs.

## Solution
The proposed solution is an automated point management system at Assembly School
consists of two main components: Odin and Heimdall. 
Odin is a server/web application
that serves as the central hub for managing student activities, points, and class information
working with a backend API and a frontend SAP application. 
Heimdall is a custom-
made point-tracking system designed to monitor and log computer usage, designed to be
deployed in all of the relevant computers and feed collected data to a data store. 
Odin
and Heimdall communicate via a Non Relational Data store where Heimdall publishes
its logs to and Odin consumes, process and adds the relevant information to the main
database. 
The usage of the frontend application and the API requires authentication via
an Office 365 account, using a Single Sign-On (SSO) mechanism. This requires the use
of an external authentication server provided by Microsoft.

## More Information
- If you want to learn more you can check out this youtube clips where we show the app working [Heimdall Component](https://youtu.be/eecV_-uHFLA) [Odin Component](https://youtu.be/oZewawVME9s)
- Or you can check our wiki for some written down information in a simplified form [Wiki](https://github.com/Rafael4DC/AssemblyOdin/wiki) 
- Or read our formal [report](https://github.com/Rafael4DC/AssemblyOdin/blob/main/docs/ReportAssemblyOdin.pdf) for this project
