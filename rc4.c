#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

#define swap(i, j) char t = i; i = j; j = t;

int main()
{
    int i;
    int cycls;
    int j = 0;
    int temp;
    char s_box[256];
    char *temp_a = malloc(32);
    int c = 0, rm;
    char key[] = {0};
    char arr[32];

    for (int i = 0; i < 15000; i++)
    {
        char arr[32];

        for (int j = 0; j < 32; j++)
        {
            arr[j] = rand();
        }
        temp_a = arr[rm];
        key[rm] = temp_a;

        if (arr[1] == 0x00)
        {
            c++;
        }
    }

    char input_text[] = "data security";

    printf("Input Text:%s\n", input_text);
    srand(time(NULL));

    for (i = 0; i < 256; ++i)
        s_box[i] = i;
    for (i = 0; i < 256; ++i)
    {
        j = (j + s_box[i] + key[i % 4]) % 256;
        swap(s_box[i], s_box[j]);
    }


    printf("Cipher Text: ");

    for (cycls = 0; input_text[cycls] != '\0'; ++cycls)
    {
        i = (i + 1) % 256;
        j = (j + s_box[i]) % 256;
        swap(s_box[i], s_box[j]);
        printf("%x ", (unsigned char)(s_box[(s_box[i] + s_box[j]) % 256] ^ input_text[cycls]));
    }

    printf("\nDump: ");
    printf("%x %x %x %x %x", input_text[0], input_text[1], input_text[2], input_text[3], input_text[4]);
    printf("\nThe frequency of event that the second RC4 key byte is 0x00:%d\n", c);


}



