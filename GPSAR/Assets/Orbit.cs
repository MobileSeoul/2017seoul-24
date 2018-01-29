using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Orbit : MonoBehaviour
{
    private float x, y, z;
    private int fb;

    // Use this for initialization
    void Start()
    {
        x = Random.Range(-100, 100);
        y = Random.Range(-100, 100);
        fb = Random.Range(0, 4);
        switch (fb)
        {
            case 0:
                z = -700;
                break;
            case 1:
                z = 700;
                break;
            case 2:
                z = -560;
                break;
            case 3:
                z = 560;
                break;
        }

    }

    // Update is called once per frame
    void Update()
    {
        transform.Rotate(Vector3.up, 100 * Time.deltaTime);
        transform.position = new Vector3(x, y, z);
        if (!SystemInfo.supportsGyroscope)
        {
            x = 0; y = 0; z = 500;
        }
        transform.position = new Vector3(x, y, z);
    }
}
