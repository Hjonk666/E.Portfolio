from hub import light_matrix

import runloop
from hub import port
import motor

async def main():
    # 1. Vis teksten «Hello World» 
    await light_matrix.write("Hello World")

    # 2. Kjøre i et rektangulært mønster
    for _ in range(2):
        # LANG SIDE: Kjøre rett frem 
        motor.run_for_time(port.A, 1000, 1000)
        await motor.run_for_time(port.C, 1000, -1000)

        # SVING TIL VENSTRE  - kun motor A roterer
        await motor.run_for_time(port.A, 720, 750)

        # KORT SIDE
        motor.run_for_time(port.A, 500, 1000)
        await motor.run_for_time(port.C, 500, -1000)

        # SVING TIL VENSTRE - kun motor A roterer 
        await motor.run_for_time(port.A, 720, 750)

    # 3. vis teksten "Have a nice day"
    await light_matrix.write("Have a nice day")

runloop.run(main())