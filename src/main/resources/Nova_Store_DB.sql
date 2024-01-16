USE [master]
GO
/****** Object:  Database [Nova_Store_DB]    Update: 13/1/2023 16:31 AM ******/
CREATE DATABASE [Nova_Store_DB]
GO
USE [Nova_Store_DB]
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Nova_Store_DB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Nova_Store_DB] SET ANSI_NULL_DEFAULT OFF
GO
ALTER DATABASE [Nova_Store_DB] SET ANSI_NULLS OFF
GO
ALTER DATABASE [Nova_Store_DB] SET ANSI_PADDING OFF
GO
ALTER DATABASE [Nova_Store_DB] SET ANSI_WARNINGS OFF
GO
ALTER DATABASE [Nova_Store_DB] SET ARITHABORT OFF
GO
ALTER DATABASE [Nova_Store_DB] SET AUTO_CLOSE ON
GO
ALTER DATABASE [Nova_Store_DB] SET AUTO_SHRINK OFF
GO
ALTER DATABASE [Nova_Store_DB] SET AUTO_UPDATE_STATISTICS ON
GO
ALTER DATABASE [Nova_Store_DB] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
ALTER DATABASE [Nova_Store_DB] SET CURSOR_DEFAULT GLOBAL
GO
ALTER DATABASE [Nova_Store_DB] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER DATABASE [Nova_Store_DB] SET NUMERIC_ROUNDABORT OFF
GO
ALTER DATABASE [Nova_Store_DB] SET QUOTED_IDENTIFIER OFF
GO
ALTER DATABASE [Nova_Store_DB] SET RECURSIVE_TRIGGERS OFF
GO
ALTER DATABASE [Nova_Store_DB] SET ENABLE_BROKER
GO
ALTER DATABASE [Nova_Store_DB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
ALTER DATABASE [Nova_Store_DB] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
ALTER DATABASE [Nova_Store_DB] SET TRUSTWORTHY OFF
GO
ALTER DATABASE [Nova_Store_DB] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO
ALTER DATABASE [Nova_Store_DB] SET PARAMETERIZATION SIMPLE
GO
ALTER DATABASE [Nova_Store_DB] SET READ_COMMITTED_SNAPSHOT OFF
GO
ALTER DATABASE [Nova_Store_DB] SET HONOR_BROKER_PRIORITY OFF
GO
ALTER DATABASE [Nova_Store_DB] SET RECOVERY SIMPLE
GO
ALTER DATABASE [Nova_Store_DB] SET MULTI_USER
GO
ALTER DATABASE [Nova_Store_DB] SET PAGE_VERIFY CHECKSUM
GO
ALTER DATABASE [Nova_Store_DB] SET DB_CHAINING OFF
GO
ALTER DATABASE [Nova_Store_DB] SET FILESTREAM ( NON_TRANSACTED_ACCESS = OFF )
GO
ALTER DATABASE [Nova_Store_DB] SET TARGET_RECOVERY_TIME = 60 SECONDS
GO
ALTER DATABASE [Nova_Store_DB] SET DELAYED_DURABILITY = DISABLED
GO
ALTER DATABASE [Nova_Store_DB] SET ACCELERATED_DATABASE_RECOVERY = OFF
GO
ALTER DATABASE [Nova_Store_DB] SET QUERY_STORE = ON
GO
ALTER DATABASE [Nova_Store_DB] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [Nova_Store_DB]
GO
/****** Object:  Table [dbo].[Account]    Script Date: 10/10/2023 9:34:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account]
(
    [Id]          [int] IDENTITY (1,1) NOT NULL,
    [Name]        [nvarchar](50)       NULL,
    [Email]       [varchar](50)        NULL,
    [PhoneNumber] [varchar](15)        NULL,
    [Password]    [varchar](255)       NULL,
    [Birthday]    [datetime]           NULL,
    [Avatar]      [varchar](255)       NULL,
    [CreateDate]  [datetime]           NULL,
    [UpdateDate]  [datetime]           NULL,
    [Status]      [int]                NULL,
    [RoleId]      [int]                NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Address]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Address]
(
    [Id]              [int] IDENTITY (1,1) NOT NULL,
    [CustomerName]    nvarchar(50)         NULL,
    [PhoneNumber]     varchar(15)          NULL,
    [SpecificAddress] [nvarchar](50)       NULL,
    [Ward]            [nvarchar](50)       NULL,
    [District]        [nvarchar](50)       NULL,
    [City]            [nvarchar](50)       NULL,
    [CreateDate]      [datetime]           NULL,
    [UpdateDate]      [datetime]           NULL,
    [Status]          [int]                NULL,
    [AccountId]       [int]                NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Bill]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Bill]
(
    [Id]               [int] IDENTITY (1,1) NOT NULL,
    [Code]             [varchar](50)        NULL,
    [Type]             [int]                NULL,
    [CustomerName]     [nvarchar](50)       NULL,
    [Address]          [nvarchar](100)      NULL,
    [PhoneNumber]      [nvarchar](15)       NULL,
    [Note]             [nvarchar](255)      NULL,
    [OrderDate]        [datetime]           NULL,
    [PaymentDate]      [datetime]           NULL,
    [ConfirmationDate] [datetime]           NULL,
    [ShippingDate]     [datetime]           NULL,
    [CompletionDate]   [datetime]           NULL,
    [CancellationDate] [datetime]           NULL,
    [ShippingFee]      [money]              NULL,
    [Price]            [money]              NULL,
    [DiscountAmount]   [money]              NULL,
    [TotalPrice]       [money]              NULL,
    [CreateDate]       [datetime]           NULL,
    [UpdateDate]       [datetime]           NULL,
    [Status]           [int]                NULL,
    [EmployeeId]       [int]                NULL,
    [CustomerId]       [int]                NULL,
    [VoucherId]        [int]                NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[BillDetail]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[BillDetail]
(
    [Id]              [int] IDENTITY (1,1) NOT NULL,
    [Price]           [money]              NULL,
    [Quantity]        [int]                NULL,
    [Status]          [int]                NULL,
    [BillId]          [int]                NULL,
    [ProductDetailId] [int]                NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Brand]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Brand]
(
    [Id]         [int] IDENTITY (1,1) NOT NULL,
    [Code]       [varchar](50)        NULL,
    [Name]       [nvarchar](50)       NULL,
    [CreateDate] [datetime]           NULL,
    [UpdateDate] [datetime]           NULL,
    [Status]     [int]                NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Cart]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Cart]
(
    [Id]         [int] IDENTITY (1,1) NOT NULL,
    [TotalPrice] [money]              NULL,
    [TotalItems] [int]                NULL,
    [AccountId]  [int]                NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[CartDetail]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[CartDetail]
(
    [Id]              [int] IDENTITY (1,1) NOT NULL,
    [Price]           [money]              NULL,
    [Quantity]        [int]                NULL,
    [CartId]          [int]                NULL,
    [ProductDetailId] [int]                NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Category]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Category]
(
    [Id]         [int] IDENTITY (1,1) NOT NULL,
    [Code]       [varchar](50)        NULL,
    [Name]       [nvarchar](50)       NULL,
    [CreateDate] [datetime]           NULL,
    [UpdateDate] [datetime]           NULL,
    [Status]     [int]                NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Color]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Color]
(
    [Id]         [int] IDENTITY (1,1) NOT NULL,
    [Code]       [varchar](50)        NULL,
    [Name]       [nvarchar](50)       NULL,
    [CreateDate] [datetime]           NULL,
    [UpdateDate] [datetime]           NULL,
    [Status]     [int]                NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Form]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Form]
(
    [Id]         [int] IDENTITY (1,1) NOT NULL,
    [Code]       [varchar](50)        NULL,
    [Name]       [nvarchar](50)       NULL,
    [CreateDate] [datetime]           NULL,
    [UpdateDate] [datetime]           NULL,
    [Status]     [int]                NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Image]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Image]
(
    [Id]         [int] IDENTITY (1,1) NOT NULL,
    [Name]       [varchar](255)       NULL,
    [CreateDate] [datetime]           NULL,
    [UpdateDate] [datetime]           NULL,
    [Status]     [int]                NULL,
    [ProductId]  [int]                NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Material]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Material]
(
    [Id]         [int] IDENTITY (1,1) NOT NULL,
    [Code]       [varchar](50)        NULL,
    [Name]       [nvarchar](50)       NULL,
    [CreateDate] [datetime]           NULL,
    [UpdateDate] [datetime]           NULL,
    [Status]     [int]                NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[PaymentMethod]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[PaymentMethod]
(
    [Id]          [int] IDENTITY (1,1) NOT NULL,
    [Name]        [nvarchar](50)       NULL,
    [Money]       [money]              NULL,
    [Description] [nvarchar](50)       NULL,
    [Status]      [int]                NULL,
    [BillId]      [int]                NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Product]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Product]
(
    [Id]          [int] IDENTITY (1,1) NOT NULL,
    [Code]        [varchar](50) UNIQUE,
    [Name]        [nvarchar](MAX)       NULL,
    [Description] [nvarchar](MAX)      NULL,
    [CreateDate]  [datetime]           NULL,
    [UpdateDate]  [datetime]           NULL,
    [Status]      [int]                NULL,
    [BrandId]     [int]                NULL,
    [CategoryId]  [int]                NULL,
    [FormId]      [int]                NULL,
    [MaterialId]  [int]                NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[ProductDetail]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[ProductDetail]
(
    [Id]            [int] IDENTITY (1,1) NOT NULL,
    [Code]          [varchar](50) UNIQUE,
    [Quantity]      [int]                NULL,
    [Price]         [money]              NULL,
    [PriceDiscount] [money]              NULL,
    [CreateDate]    [datetime]           NULL,
    [UpdateDate]    [datetime]           NULL,
    [Status]        [int]                NULL,
    [ProductId]     [int]                NULL,
    [SizeId]        [int]                NULL,
    [ColorId]       [int]                NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Promotion]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Promotion]
(
    [Id]         [int] IDENTITY (1,1) NOT NULL,
    [Code]       [varchar](255)       NULL,
    [Name]       [nvarchar](50)       NULL,
    [Value]      [float]              NULL,
    [StartDate]  [datetime]           NULL,
    [EndDate]    [datetime]           NULL,
    [CreateDate] [datetime]           NULL,
    [UpdateDate] [datetime]           NULL,
    [Status]     [int]                NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[PromotionDetail]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[PromotionDetail]
(
    [Id]          [int] IDENTITY (1,1) NOT NULL,
    [CreateDate]  [datetime]           NULL,
    [UpdateDate]  [datetime]           NULL,
    [Status]      [int]                NULL,
    [ProductId]   [int]                NULL,
    [PromotionId] [int]                NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Role]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Role]
(
    [Id]         [int] IDENTITY (1,1) NOT NULL,
    [Name]       [nvarchar](50)       NULL,
    [CreateDate] [datetime]           NULL,
    [UpdateDate] [datetime]           NULL,
    [Status]     [int]                NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Size]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Size]
(
    [Id]         [int] IDENTITY (1,1) NOT NULL,
    [Code]       [varchar](50)        NULL,
    [Name]       [nvarchar](50)       NULL,
    [CreateDate] [datetime]           NULL,
    [UpdateDate] [datetime]           NULL,
    [Status]     [int]                NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Voucher]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Voucher]
(
    [Id]           [int] IDENTITY (1,1) NOT NULL,
    [Code]         [varchar](50)        NULL,
    [Name]         [nvarchar](50)       NULL,
    [Value]        [money]              NULL,
    [Quantity]     [int]                NULL,
    [MinimumPrice] [money]              NULL,
    [StartDate]    [datetime]           NULL,
    [EndDate]      [datetime]           NULL,
    [CreateDate]   [datetime]           NULL,
    [UpdateDate]   [datetime]           NULL,
    [Status]       [int]                NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
ALTER TABLE [dbo].[Account]
    WITH CHECK ADD CONSTRAINT [FKi9xmahyh65di7x2wn5fvt8lv3] FOREIGN KEY ([RoleId])
    REFERENCES [dbo].[Role] ([Id])
    GO
ALTER TABLE [dbo].[Account]
    CHECK CONSTRAINT [FKi9xmahyh65di7x2wn5fvt8lv3]
    GO
ALTER TABLE [dbo].[Address]
    WITH CHECK ADD CONSTRAINT [FK3haj5uqn2j6ar58mcglooa5bp] FOREIGN KEY ([AccountId])
    REFERENCES [dbo].[Account] ([Id])
    GO
ALTER TABLE [dbo].[Address]
    CHECK CONSTRAINT [FK3haj5uqn2j6ar58mcglooa5bp]
    GO
ALTER TABLE [dbo].[Bill]
    WITH CHECK ADD CONSTRAINT [FK5zxcs2c3vdsauy6541vgj2y5t] FOREIGN KEY ([EmployeeId])
    REFERENCES [dbo].[Account] ([Id])
    GO
ALTER TABLE [dbo].[Bill]
    CHECK CONSTRAINT [FK5zxcs2c3vdsauy6541vgj2y5t]
    GO
ALTER TABLE [dbo].[Bill]
    WITH CHECK ADD CONSTRAINT [FK5mrre5s0gacpqu6737kfocwkl] FOREIGN KEY ([CustomerId])
    REFERENCES [dbo].[Account] ([Id])
    GO
ALTER TABLE [dbo].[Bill]
    CHECK CONSTRAINT [FK5mrre5s0gacpqu6737kfocwkl]
    GO
ALTER TABLE [dbo].[Bill]
    WITH CHECK ADD CONSTRAINT [FK2hf3g6padqdy15tccpshmpxob] FOREIGN KEY ([VoucherId])
    REFERENCES [dbo].[Voucher] ([Id])
    GO
ALTER TABLE [dbo].[Bill]
    CHECK CONSTRAINT [FK2hf3g6padqdy15tccpshmpxob]
    GO
ALTER TABLE [dbo].[BillDetail]
    WITH CHECK ADD CONSTRAINT [FK8sw1tfhht3q5xtdsyoe0r7jfd] FOREIGN KEY ([BillId])
    REFERENCES [dbo].[Bill] ([Id])
    GO
ALTER TABLE [dbo].[BillDetail]
    CHECK CONSTRAINT [FK8sw1tfhht3q5xtdsyoe0r7jfd]
    GO
ALTER TABLE [dbo].[BillDetail]
    WITH CHECK ADD CONSTRAINT [FKnt7lacod5l24jdnfgxfydqiu2] FOREIGN KEY ([ProductDetailId])
    REFERENCES [dbo].[ProductDetail] ([Id])
    GO
ALTER TABLE [dbo].[BillDetail]
    CHECK CONSTRAINT [FKnt7lacod5l24jdnfgxfydqiu2]
    GO
ALTER TABLE [dbo].[Cart]
    WITH CHECK ADD CONSTRAINT [FK1w1km3ww10t0maawf2cymyx5i] FOREIGN KEY ([AccountId])
    REFERENCES [dbo].[Account] ([Id])
    GO
ALTER TABLE [dbo].[Cart]
    CHECK CONSTRAINT [FK1w1km3ww10t0maawf2cymyx5i]
    GO
ALTER TABLE [dbo].[CartDetail]
    WITH CHECK ADD CONSTRAINT [FK6unelr9lsy26gw9da5tuuxcsh] FOREIGN KEY ([CartId])
    REFERENCES [dbo].[Cart] ([Id])
    GO
ALTER TABLE [dbo].[CartDetail]
    CHECK CONSTRAINT [FK6unelr9lsy26gw9da5tuuxcsh]
    GO
ALTER TABLE [dbo].[CartDetail]
    WITH CHECK ADD CONSTRAINT [FKepuvpwbykpahqt0gagvvdqoyn] FOREIGN KEY ([ProductDetailId])
    REFERENCES [dbo].[ProductDetail] ([Id])
    GO
ALTER TABLE [dbo].[CartDetail]
    CHECK CONSTRAINT [FKepuvpwbykpahqt0gagvvdqoyn]
    GO
ALTER TABLE [dbo].[Image]
    WITH CHECK ADD CONSTRAINT [FKdtaisglfgjjj5j1a7g3fcev7c] FOREIGN KEY ([ProductId])
    REFERENCES [dbo].[Product] ([Id])
    GO
ALTER TABLE [dbo].[Image]
    CHECK CONSTRAINT [FKdtaisglfgjjj5j1a7g3fcev7c]
    GO
ALTER TABLE [dbo].[PaymentMethod]
    WITH CHECK ADD CONSTRAINT [FKjity6x6p1194mtoli4abb3jgc] FOREIGN KEY ([BillId])
    REFERENCES [dbo].[Bill] ([Id])
    GO
ALTER TABLE [dbo].[PaymentMethod]
    CHECK CONSTRAINT [FKjity6x6p1194mtoli4abb3jgc]
    GO
ALTER TABLE [dbo].[Product]
    WITH CHECK ADD CONSTRAINT [FK4cx1ir1xlnytlte2quullny9m] FOREIGN KEY ([FormId])
    REFERENCES [dbo].[Form] ([Id])
    GO
ALTER TABLE [dbo].[Product]
    CHECK CONSTRAINT [FK4cx1ir1xlnytlte2quullny9m]
    GO
ALTER TABLE [dbo].[Product]
    WITH CHECK ADD CONSTRAINT [FK6pnobu31k3yhhmk45s97imkui] FOREIGN KEY ([CategoryId])
    REFERENCES [dbo].[Category] ([Id])
    GO
ALTER TABLE [dbo].[Product]
    CHECK CONSTRAINT [FK6pnobu31k3yhhmk45s97imkui]
    GO
ALTER TABLE [dbo].[Product]
    WITH CHECK ADD CONSTRAINT [FKfc8uiunvrolmn3qa9ahrhmtrw] FOREIGN KEY ([MaterialId])
    REFERENCES [dbo].[Material] ([Id])
    GO
ALTER TABLE [dbo].[Product]
    CHECK CONSTRAINT [FKfc8uiunvrolmn3qa9ahrhmtrw]
    GO
ALTER TABLE [dbo].[Product]
    WITH CHECK ADD CONSTRAINT [FKnuw1iwpj73v904j79uc8qurgc] FOREIGN KEY ([BrandId])
    REFERENCES [dbo].[Brand] ([Id])
    GO
ALTER TABLE [dbo].[Product]
    CHECK CONSTRAINT [FKnuw1iwpj73v904j79uc8qurgc]
    GO
ALTER TABLE [dbo].[ProductDetail]
    WITH CHECK ADD CONSTRAINT [FKik38y3bjry9u05majdn5u3egj] FOREIGN KEY ([ProductId])
    REFERENCES [dbo].[Product] ([Id])
    GO
ALTER TABLE [dbo].[ProductDetail]
    CHECK CONSTRAINT [FKik38y3bjry9u05majdn5u3egj]
    GO
ALTER TABLE [dbo].[ProductDetail]
    WITH CHECK ADD CONSTRAINT [FK61xkqx42jtcc8we64hahp05pv] FOREIGN KEY ([SizeId])
    REFERENCES [dbo].[Size] ([Id])
    GO
ALTER TABLE [dbo].[ProductDetail]
    CHECK CONSTRAINT [FK61xkqx42jtcc8we64hahp05pv]
    GO
ALTER TABLE [dbo].[ProductDetail]
    WITH CHECK ADD CONSTRAINT [FKntlsi9s4irkogtc9mbw03s90y] FOREIGN KEY ([ColorId])
    REFERENCES [dbo].[Color] ([Id])
    GO
ALTER TABLE [dbo].[ProductDetail]
    CHECK CONSTRAINT [FKntlsi9s4irkogtc9mbw03s90y]
    GO
ALTER TABLE [dbo].[PromotionDetail]
    WITH CHECK ADD CONSTRAINT [FKa83ktjk5axkasy5c9v2s1ukig] FOREIGN KEY ([ProductId])
    REFERENCES [dbo].[Product] ([Id])
    GO
ALTER TABLE [dbo].[PromotionDetail]
    CHECK CONSTRAINT [FKa83ktjk5axkasy5c9v2s1ukig]
    GO
ALTER TABLE [dbo].[PromotionDetail]
    WITH CHECK ADD CONSTRAINT [FKos6ftbatvw4mk81km7xpkds5i] FOREIGN KEY ([PromotionId])
    REFERENCES [dbo].[Promotion] ([Id])
    GO
ALTER TABLE [dbo].[PromotionDetail]
    CHECK CONSTRAINT [FKos6ftbatvw4mk81km7xpkds5i]
    GO
    USE [master]
    GO
ALTER DATABASE [Nova_Store_DB] SET READ_WRITE
GO

USE Nova_Store_DB

SELECT * FROM Brand
    INSERT INTO Brand (code, name, CreateDate, UpdateDate, Status)
VALUES ('TH0001', N'Reebok', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (code, name, CreateDate, UpdateDate, Status)
VALUES ('TH0002', N'Asics', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (code, name, CreateDate, UpdateDate, Status)
VALUES ('TH0003', N'Brooks', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (code, name, CreateDate, UpdateDate, Status)
VALUES ('TH0004', N'Mizuno', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (code, name, CreateDate, UpdateDate, Status)
VALUES ('TH0005', N'New balance', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (code, name, CreateDate, UpdateDate, Status)
VALUES ('TH0006', N'Puma', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (code, name, CreateDate, UpdateDate, Status)
VALUES ('TH0007', N'Nike', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (code, name, CreateDate, UpdateDate, Status)
VALUES ('TH0008', N'Skechers', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (code, name, CreateDate, UpdateDate, Status)
VALUES ('TH0009', N'Under armour', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (code, name, CreateDate, UpdateDate, Status)
VALUES ('TH0010', N'Adidas', GETDATE(), GETDATE(), 1)


SELECT * FROM Category
    INSERT INTO Category (code, name, CreateDate, UpdateDate, Status)
VALUES ('L0001', N'Áo thun', GETDATE(), GETDATE(), 1)
INSERT INTO Category (code, name, CreateDate, UpdateDate, Status)
VALUES ('L0002', N'Áo khoác', GETDATE(), GETDATE(), 1)
INSERT INTO Category (code, name, CreateDate, UpdateDate, Status)
VALUES ('L0003', N'Áo ngắn tay', GETDATE(), GETDATE(), 1)
INSERT INTO Category (code, name, CreateDate, UpdateDate, Status)
VALUES ('L0004', N'Áo Polo', GETDATE(), GETDATE(), 1)
INSERT INTO Category (code, name, CreateDate, UpdateDate, Status)
VALUES ('L0005', N'Áo dài tay', GETDATE(), GETDATE(), 1)
INSERT INTO Category (code, name, CreateDate, UpdateDate, Status)
VALUES ('L0006', N'Quần bó', GETDATE(), GETDATE(), 1)
INSERT INTO Category (code, name, CreateDate, UpdateDate, Status)
VALUES ('L0007', N'Quần cạp cao', GETDATE(), GETDATE(), 1)
INSERT INTO Category (code, name, CreateDate, UpdateDate, Status)
VALUES ('L0008', N'Quần đùi', GETDATE(), GETDATE(), 1)
INSERT INTO Category (code, name, CreateDate, UpdateDate, Status)
VALUES ('L0009', N'Quần dài', GETDATE(), GETDATE(), 1)
INSERT INTO Category (code, name, CreateDate, UpdateDate, Status)
VALUES ('L0010', N'Quần legging', GETDATE(), GETDATE(), 1)

SELECT * FROM Material
    INSERT INTO Material (code, name, CreateDate, UpdateDate, Status)
VALUES ('CL0001', N'Spandex', GETDATE(), GETDATE(), 1)
INSERT INTO Material (code, name, CreateDate, UpdateDate, Status)
VALUES ('CL0002', N'Cotton', GETDATE(), GETDATE(), 1)
INSERT INTO Material (code, name, CreateDate, UpdateDate, Status)
VALUES ('CL0003', N'Mesh', GETDATE(), GETDATE(), 1)
INSERT INTO Material (code, name, CreateDate, UpdateDate, Status)
VALUES ('CL0004', N'Nylon', GETDATE(), GETDATE(), 1)
INSERT INTO Material (code, name, CreateDate, UpdateDate, Status)
VALUES ('CL0005', N'Viscose', GETDATE(), GETDATE(), 1)
INSERT INTO Material (code, name, CreateDate, UpdateDate, Status)
VALUES ('CL0006', N'Microfiber', GETDATE(), GETDATE(), 1)
INSERT INTO Material (code, name, CreateDate, UpdateDate, Status)
VALUES ('CL0007', N'Linen', GETDATE(), GETDATE(), 1)
INSERT INTO Material (code, name, CreateDate, UpdateDate, Status)
VALUES ('CL0008', N'Syntheic', GETDATE(), GETDATE(), 1)
INSERT INTO Material (code, name, CreateDate, UpdateDate, Status)
VALUES ('CL0009', N'Polyester', GETDATE(), GETDATE(), 1)
INSERT INTO Material (code, name, CreateDate, UpdateDate, Status)
VALUES ('CL0010', N'Fleece', GETDATE(), GETDATE(), 1)


SELECT * FROM Form
    INSERT INTO Form (code, name, CreateDate, UpdateDate, Status)
VALUES ('KD0001', N'Bó', GETDATE(), GETDATE(), 1)
INSERT INTO Form (code, name, CreateDate, UpdateDate, Status)
VALUES ('KD0002', N'Rộng', GETDATE(), GETDATE(), 1)
INSERT INTO Form (code, name, CreateDate, UpdateDate, Status)
VALUES ('KD0003', N'Tank top', GETDATE(), GETDATE(), 1)
INSERT INTO Form (code, name, CreateDate, UpdateDate, Status)
VALUES ('KD0004', N'Jogger', GETDATE(), GETDATE(), 1)
INSERT INTO Form (code, name, CreateDate, UpdateDate, Status)
VALUES ('KD0005', N'Dài', GETDATE(), GETDATE(), 1)
INSERT INTO Form (code, name, CreateDate, UpdateDate, Status)
VALUES ('KD0006', N'Ngắn', GETDATE(), GETDATE(), 1)
INSERT INTO Form (code, name, CreateDate, UpdateDate, Status)
VALUES ('KD0007', N'Dày', GETDATE(), GETDATE(), 1)
INSERT INTO Form (code, name, CreateDate, UpdateDate, Status)
VALUES ('KD0008', N'Mỏng', GETDATE(), GETDATE(), 1)
INSERT INTO Form (code, name, CreateDate, UpdateDate, Status)
VALUES ('KD0009', N'Lưới', GETDATE(), GETDATE(), 1)
INSERT INTO Form (code, name, CreateDate, UpdateDate, Status)
VALUES ('KD0010', N'Thiếu vải', GETDATE(), GETDATE(), 1)


SELECT * FROM Size
    INSERT INTO Size (code, name, CreateDate, UpdateDate, Status)
VALUES ('S0001', N'28', GETDATE(), GETDATE(), 1)
INSERT INTO Size (code, name, CreateDate, UpdateDate, Status)
VALUES ('S0002', N'29', GETDATE(), GETDATE(), 1)
INSERT INTO Size (code, name, CreateDate, UpdateDate, Status)
VALUES ('S0003', N'30', GETDATE(), GETDATE(), 1)
INSERT INTO Size (code, name, CreateDate, UpdateDate, Status)
VALUES ('S0004', N'31', GETDATE(), GETDATE(), 1)
INSERT INTO Size (code, name, CreateDate, UpdateDate, Status)
VALUES ('S0005', N'32', GETDATE(), GETDATE(), 1)
INSERT INTO Size (code, name, CreateDate, UpdateDate, Status)
VALUES ('S0006', N'XXL', GETDATE(), GETDATE(), 1)
INSERT INTO Size (code, name, CreateDate, UpdateDate, Status)
VALUES ('S0007', N'XL', GETDATE(), GETDATE(), 1)
INSERT INTO Size (code, name, CreateDate, UpdateDate, Status)
VALUES ('S0008', N'L', GETDATE(), GETDATE(), 1)
INSERT INTO Size (code, name, CreateDate, UpdateDate, Status)
VALUES ('S0009', N'S', GETDATE(), GETDATE(), 1)
INSERT INTO Size (code, name, CreateDate, UpdateDate, Status)
VALUES ('S0010', N'M', GETDATE(), GETDATE(), 1)


SELECT * FROM Color
    INSERT INTO Color (code, name, CreateDate, UpdateDate, Status)
VALUES ('M0001', N'Đen', GETDATE(), GETDATE(), 1)
INSERT INTO Color (code, name, CreateDate, UpdateDate, Status)
VALUES ('M0002', N'Đỏ', GETDATE(), GETDATE(), 1)
INSERT INTO Color (code, name, CreateDate, UpdateDate, Status)
VALUES ('M0003', N'Xanh dương', GETDATE(), GETDATE(), 1)
INSERT INTO Color (code, name, CreateDate, UpdateDate, Status)
VALUES ('M0004', N'Xanh lá', GETDATE(), GETDATE(), 1)
INSERT INTO Color (code, name, CreateDate, UpdateDate, Status)
VALUES ('M0005', N'Vàng', GETDATE(), GETDATE(), 1)
INSERT INTO Color (code, name, CreateDate, UpdateDate, Status)
VALUES ('M0006', N'Trắng', GETDATE(), GETDATE(), 1)
INSERT INTO Color (code, name, CreateDate, UpdateDate, Status)
VALUES ('M0007', N'Xám', GETDATE(), GETDATE(), 1)
INSERT INTO Color (code, name, CreateDate, UpdateDate, Status)
VALUES ('M0008', N'Ghi', GETDATE(), GETDATE(), 1)
INSERT INTO Color (code, name, CreateDate, UpdateDate, Status)
VALUES ('M0009', N'Nâu', GETDATE(), GETDATE(), 1)
INSERT INTO Color (code, name, CreateDate, UpdateDate, Status)
VALUES ('M0010', N'Cam', GETDATE(), GETDATE(), 1)


SELECT * FROM Product
    INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00001', N'Áo nam thể thao NOVA logo nổi 3D 7 màu vải xuất', N'Không nhăn, không co rút khi giặt, không cần ủi trước khi mặc. Phom tiêu chuẩn dễ mặc, thích hợp với mọi vóc dáng, phong cách năng động.', GETDATE(), GETDATE(), 1, 1, 1, 1, 1);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00002', N'Quần đùi thể thao nam thun NOVA phối chữ dọc ống', N'Mỏng nhẹ, thấm hút mồ hôi nhanh, luôn khô thoáng. Không nhăn, Không biến dạng (co, rút) khi giặt, không cần ủi/là', GETDATE(), GETDATE(), 1, 2, 2, 2, 2);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00003', N'Quần đùi chạy bộ NOVA', N'Không nhăn, không co rút khi giặt, không cần ủi trước khi mặc. Phom tiêu chuẩn dễ mặc, thích hợp với mọi vóc dáng, phong cách năng động.', GETDATE(), GETDATE(), 1, 3, 3, 3, 3);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00004', N'Áo ba lỗ Tank top Nam Cotton YL Lưng Phối', N'Phom tiêu chuẩn dễ mặc, thích hợp với mọi vóc dáng, phong cách năng động. Bạn có thể mặc khi chơi thể thao, đi chơi, đi làm, ở nhà,…', GETDATE(), GETDATE(), 1, 4, 4, 4, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00005', N'Quần đùi thể thao nam A-CHILL TOUCH Anta',N'Chất liệu vải cao cấp, mang lại cảm giác thoải mái cho bạn trong suốt quá trình hoạt động. ' , GETDATE(), GETDATE(), 1, 5, 5, 5, 5);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00006', N'Quần dài thể thao nam NOVA', N'Sản phẩm luôn căng mịn, không nhăn và chảy xệ giúp tôn vinh đường nét của cơ thể. Tạo cảm giác thoải mái khi vận động. ', GETDATE(), GETDATE(), 1, 6, 6, 6, 6);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00007', N'Quần dài thể thao nam Anta', N'Quần dài thể thao nam Anta được thiết kế chuyên biệt cho các hoạt động thể thao, dã ngoại, đi chơi, form vừa vặn, giúp bạn vừa thể hiện phong cách thời trang của mình, vừa thoải mái trong mọi hoạt động, mang lại sự tự tin trước những người xung quanh.', GETDATE(), GETDATE(), 1, 7, 7, 7, 7);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00008', N'Áo thể thao nam Cross-training A-CHILL TOUCH Anta', N'Áo thể thao nam Cross-training A-CHILL TOUCH Anta với chất liệu vải cao cấp được thiết kế chuyên biệt cho các hoạt động thể thao, dã ngoại, đi chơi.', GETDATE(), GETDATE(), 1, 8, 8, 8, 8);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00009', N'Áo dài tay thể thao nam Running A-DRY FAST Anta', N'với chất liệu vải cao cấp được thiết kế chuyên biệt cho các hoạt động thể thao, dã ngoại, đi chơi. Chất liệu vải cao cấp, giữ ấm tốt, mang lại cảm giác thoải mái cho bạn trong suốt quá trình hoạt động. Sản phẩm luôn căng mịn, không nhăn và chảy xệ giúp tôn vinh đường nét của cơ thể. Tạo cảm giác thoải mái khi vận động. Sản phẩm ôm sát cơ thể nhưng mềm mịn và ôm sát nhẹ nhàng vào cơ thể nên bạn vẫn cảm thấy rất dễ thở khi tập luyện.', GETDATE(), GETDATE(), 1, 9, 9, 9, 9);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00010', N'Quần đùi thể thao nam Running A-COOL', N'với chất liệu vải cao cấp được thiết kế chuyên biệt cho các hoạt động thể thao, dã ngoại, đi chơi. Chất liệu vải cao cấp mang lại cảm giác thoải mái cho bạn trong suốt quá trình hoạt động. Sản phẩm luôn căng mịn, không nhăn và chảy xệ giúp tôn vinh đường nét của cơ thể. Sản phẩm ôm sát cơ thể nhưng mềm mịn và ôm sát nhẹ nhàng vào cơ thể nên bạn vẫn cảm thấy rất dễ thở khi tập luyện.', GETDATE(), GETDATE(), 1, 10, 10, 10, 10);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00011', N'Áo ba lỗ thể thao nam A-COOL', N'với chất liệu vải cao cấp được thiết kế chuyên biệt cho các hoạt động thể thao, dã ngoại, đi chơi. Chất liệu vải cao cấp, mang lại cảm giác thoải mái cho bạn trong suốt quá trình hoạt động. Sản phẩm luôn căng mịn, không nhăn và chảy xệ giúp tôn vinh đường nét của cơ thể. Tạo cảm giác thoải mái khi vận động. Dễ dàng phối hợp với nhiều trang phục và phụ kiện tạo nên cho bạn một phong thời trang thật trẻ trung, năng động.', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00012', N'Áo ba lỗ thể thao lưới kim logo ngực lưng YL', N'Chất vải mỏng nhẹ, thấm hút mồ hôi nhanh, luôn khô thoáng ngay cả khi bạn đang vận động. Bạn có thể mặc khi chơi thể thao, đi chơi, đi làm, ở nhà,…', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00013', N'Áo tay dài nam giữ nhiệt Body UV', N'Chất vải mỏng nhẹ, thấm hút mồ hôi nhanh, luôn khô thoáng ngay cả khi bạn đang vận động.', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00014', N'Áo thun thể thao nam Yolo vạch PQ tay thoáng khí', N'Bạn có thể mặc khi chơi thể thao, đi chơi, đi làm, ở nhà,… Phom tiêu chuẩn dễ mặc, thích hợp với mọi vóc dáng, phong cách năng động.', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00015', N'Áo thể thao nam Marvel', N'Áo kéo khóa, cổ bản 6cm, túi xẻ không khóa kéo, dệt bo gấu áo gọn gàng. Quần cạp chun dây rút co giãn thoải mái và túi xẻ 2 bên tiện lợi. Dệt terry nỉ xéo giúp sản phẩm có độ mềm mại, đồng thời vẫn dày dặn, đứng dáng. Thiết kế basic, màu sắc nam tính kết hợp hiệu ứng màu melange cho vẻ ngoài thu hút.', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00016', N'Áo khoác thu đông thể thao NOVA', N'Thiết kế Thiết kế áo cổ trụ có khóa kéo, bo viền cổ tay và gấu áo, quần cạp chun dễ chịu, gấu quần bo nhẹ tạo nét khỏe khoắn và nam tính.', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00017', N'Quần dài thu đông Nova', N'Thiết kế basic với túi xẻ hai bên cạp chun co giãn dễ chịu thoải mái với người mặc. Màu sắc nam tính, dễ kết hợp trang phục.', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00018', N'Quần Shorts thể thao 7" New Ultra', N'Nhanh khô do ứng dụng công nghệ Quickdry giúp tạo cảm giác khô ráo, thoáng khí khi vận động. Đặc điểm này cũng rất hữu ích vào những ngày mưa nhiều hoặc những ngày nồm ẩm đặc trưng của miền Bắc.', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00019', N'Áo khoác thể thao form mỏng', N'Phom tiêu chuẩn dễ mặc, thích hợp với mọi vóc dáng, phong cách năng động. Không nhăn, không co rút khi giặt, không cần ủi trước khi mặc.', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00020', N'Áo khoác thể thao nam in phản quang', N'Bộ đồ nam thể thao - nam tính, năng động, thoải mái Hạn chế nhăn nhàu, giữ form tốt, chống tĩnh điện', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00021', N'Áo khoác thể thao nam nỉ cổ dựng in sườn', N'Xốp nhẹ và giữ ấm: Cấu trúc double face được thiết kế như chiếc bánh sandwich với lớp giữa mềm xốp chứa đựng không khí tĩnh. Tăng cường khả năng giữ ấm cơ thể Giữ phom - Không bai dão: Cấu trúc với 4 hệ sợi liên kết chặt chẽ giúp bề mặt ổn định về kết cấu, không làm biến dạng trong quá trình sử dụng', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00022', N'Áo Tanktop Thể Thao Nam Sát Nách Form Regular', N'Áo Tanktop Thể Thao Nam Sát Nách Form Regular là một item có tính chất mỏng nhẹ, thoáng mát nên rất thích hợp cho các hoạt động thể thao. Mang form dáng rộng rãi, thoải mái kết hợp chất liệu bền đẹp, chống nhăn, chống khuẩn tốt. Thân trước được thiết kế trơn, phía sau được thêm vào họa tiết in cá tính, là chiếc áo thun đáp ứng đầy đủ các tiêu chí để tập luyện thể thao.', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00023', N'Áo Thun Thể Thao Nam Tay Ngắn Rã Vai Form Fitted', N'Áo Thun Thể Thao Nam Tay Ngắn Rã Vai Form Fitted là một item có tính chất mỏng nhẹ, thoáng mát nên rất thích hợp cho các hoạt động thể thao. Mang form dáng rộng rãi, thoải mái kết hợp chất liệu bền đẹp, chống nhăn, chống khuẩn tốt. Thân trước được thiết kế trơn, phía sau được thêm vào họa tiết in cá tính, là chiếc áo thun đáp ứng đầy đủ các tiêu chí để tập luyện thể thao.', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00024', N'Áo Thun Thể Thao Nam Tay Ngắn Phối Màu Form Slim', N'Áo Thun Tay Ngắn Nam Form Slim là sản phẩm nằm trong bộ sưu tập "Active Wear". Áo được sử dụng chất vải nhẹ, trượt nước, co giãn 4 chiều và được dệt kim nguyên vòng với kỹ thuật thoáng khí. Thiết kế không đường may tinh tế ôm vừa cơ thể, tay áo raglan kết hợp kiểu dệt gân ở hai bên sườn và trước ngực tôn lên vòng ngực, tạo cảm giác thon gọn người mặc.', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00025', N'Áo Hoodie Thể Thao Tay Ngắn Có Nón Form Regular', N'Áo Hoodie Thể Thao Tay Ngắn Có Nón Form Regular với phong cách thiết kế phá cách, mới lạ với form rộng, tay ngắn, có nón giúp cho người mặc tự tin phối nhiều style trang phục khác nhau. Chất vải pha Nylon Spandex kết hợp với kiểu dáng cổ điển, suông thẳng, vừa vặn vào phần cơ thể mang đến sự nhẹ nhàng, thoáng mát và dễ chịu cho người mặc. Áo hoodie form regular không chỉ đem đến sự thoải mái khi hoạt động mà còn giữ ấm trong thời tiết tập luyện lạnh.', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00026', N'Quần Short tập Gym nam 2 lớp NOVA', N'Quần short tập gym nam 2 lớp Navy là một sản phẩm thời trang dành cho những người yêu thích thể hình và sức khỏe. Quần có thiết kế đơn giản nhưng hiện đại, với 2 lớp vải chất lượng cao, thoáng mát và co giãn tốt. Quần có nhiều màu sắc và kích cỡ để bạn lựa chọn, phù hợp với mọi phong cách và dáng người. Quần short tập gym nam 2 lớp Navy không chỉ giúp bạn thoải mái khi tập luyện, mà còn tôn lên vẻ đẹp nam tính và khỏe khoắn của bạn. Quần cũng rất dễ phối đồ bạn có thể kết hợp với áo thun, áo sơ mi hay áo khoác… Để tạo nên những bộ trang phục năng động và cá tính.', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00027', N'Quần Short Thể Thao Xẻ Lai Dây Rút Form Regular', N'Quần Short Thể Thao Xẻ Lai Dây Rút Form Regular là thiết kế dành riêng cho các chàng trai đam mê thể thao. Với chất vải nhẹ co giãn, không thấm nước và kỹ thuật in chuyển nhiệt mang lại chất lượng sắc nét, bền màu hơn, quần short in chuyển nhiệt có thể giặt và ủi trực tiếp mà không ảnh hưởng đến màu săc, chất lượng. Form quần rộng rãi, ống suống thẳng đứng từ trên xuống cho người mặc thoải mái vận động, chi tiết xẻ lai ngắn ở ống quần tạo điểm nhấn riêng biệt hơn.', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00028', N'Áo thể thao nam NOVA Powerfit logo chìm', N'Chất vải mỏng nhẹ, thấm hút mồ hôi nhanh, luôn khô thoáng ngay cả khi bạn đang vận động. Bạn có thể mặc khi chơi thể thao, đi chơi, đi làm, ở nhà,…', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00029', N'Quần đùi thể thao siêu thoáng lỗ kim NOVA CoolFit Tech', N'Chất vải mỏng nhẹ, thấm hút mồ hôi nhanh, luôn khô thoáng ngay cả khi bạn đang vận động.  Phom tiêu chuẩn dễ mặc, thích hợp với mọi vóc dáng, phong cách năng động.', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00030', N'Quần dài thể thao không nhăn', N'Không nhăn, không co rút khi giặt, không cần ủi trước khi mặc. Phom tiêu chuẩn dễ mặc, thích hợp với mọi vóc dáng, phong cách năng động.', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00031', N'Áo khoác thể thao chạy bộ NOVA CoolFit Tech', N'Chất vải mỏng nhẹ, thấm hút mồ hôi nhanh, luôn khô thoáng ngay cả khi bạn đang vận động.  Phom tiêu chuẩn dễ mặc, thích hợp với mọi vóc dáng, phong cách năng động.', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP00032', N'Áo khoác thu đông thể thao', N'Không nhăn, không co rút khi giặt, không cần ủi trước khi mặc. Phom tiêu chuẩn dễ mặc, thích hợp với mọi vóc dáng, phong cách năng động.', GETDATE(), GETDATE(), 1, 1, 2,3, 4);

USE Nova_Store_DB
SELECT * FROM Product

SELECT * FROM ProductDetail
    INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00001', 12, 490000, 490000, GETDATE(), GETDATE(), 1, 1, 1, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00002', 22, 90000, 90000, GETDATE(), GETDATE(), 1, 2, 2, 3);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00003', 30, 100000, 100000, GETDATE(), GETDATE(), 1, 3, 1, 2);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00004', 40, 670000, 670000, GETDATE(), GETDATE(), 1, 4, 2, 3);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00005', 31, 1300000, 1300000, GETDATE(), GETDATE(), 1, 5, 3, 4);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00006', 12, 250000, 250000, GETDATE(), GETDATE(), 1, 6, 5, 6);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00007', 11, 500000, 500000, GETDATE(), GETDATE(), 1, 7, 7, 8);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00008', 60, 4000000, 4000000, GETDATE(), GETDATE(), 1, 8, 9, 10);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00009', 27, 5500000, 5500000, GETDATE(), GETDATE(), 1, 9, 2, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00010', 38, 2400000, 2400000, GETDATE(), GETDATE(), 1, 10, 4, 3);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00011', 36, 7800000, 7800000, GETDATE(), GETDATE(), 1, 11, 4, 9);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00012', 18, 999000, 999000, GETDATE(), GETDATE(), 1, 12, 2, 7);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00013', 24, 4400000, 4400000, GETDATE(), GETDATE(), 1, 13, 3, 8);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00014', 44, 346000, 346000, GETDATE(), GETDATE(), 1, 14, 10, 4);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00015', 55, 89000, 89000, GETDATE(), GETDATE(), 1, 15, 6, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00016', 46, 5500000, 5500000, GETDATE(), GETDATE(), 1, 16, 3, 5);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00017', 71, 456000, 456000, GETDATE(), GETDATE(), 1, 17, 1, 4);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00018', 28, 8000000, 8000000, GETDATE(), GETDATE(), 1, 18, 9, 3);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00019', 23, 9800000, 9800000, GETDATE(), GETDATE(), 1, 19, 10, 2);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00020', 26, 2300000, 2300000, GETDATE(), GETDATE(), 1, 20, 2, 4);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00021', 2, 340000, 340000, GETDATE(), GETDATE(), 1, 21, 5, 8);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00022', 300, 6880000, 6880000, GETDATE(), GETDATE(), 1, 22, 6, 2);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00023', 77, 250000, 250000, GETDATE(), GETDATE(), 1, 23, 1, 9);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00024', 210, 85000, 85000, GETDATE(), GETDATE(), 1, 24, 6, 5);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00025', 99, 67000, 67000, GETDATE(), GETDATE(), 1, 25, 7, 8);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00026', 100, 35000, 35000, GETDATE(), GETDATE(), 1, 26, 7, 10);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00027', 46, 45000, 45000, GETDATE(), GETDATE(), 1, 27, 2, 8);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00028', 340, 1000000, 1000000, GETDATE(), GETDATE(), 1, 28, 5, 3);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00029', 290, 300000, 300000, GETDATE(), GETDATE(), 1, 29, 6, 9);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00030', 40, 5700000, 5700000, GETDATE(), GETDATE(), 1, 30, 1, 6);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00031', 128, 2650000, 2650000, GETDATE(), GETDATE(), 1, 10, 2, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00032', 38, 8000000, 8000000, GETDATE(), GETDATE(), 1, 11, 2, 9);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00033', 282, 950000, 950000, GETDATE(), GETDATE(), 1, 12, 5, 7);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00034', 382, 4500000, 4500000, GETDATE(), GETDATE(), 1, 13, 5, 4);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00035', 311, 300000, 300000, GETDATE(), GETDATE(), 1, 14, 5, 5);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00036', 111, 99000, 99000, GETDATE(), GETDATE(), 1, 15, 9, 7);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00037', 112, 5600000, 5600000, GETDATE(), GETDATE(), 1, 16, 10, 10);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00038', 138, 430000, 430000, GETDATE(), GETDATE(), 1, 17, 5, 3);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00039', 222, 7800000, 7800000, GETDATE(), GETDATE(), 1, 18, 6, 6);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00040', 100, 10000000, 10000000, GETDATE(), GETDATE(), 1, 19, 2, 10);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00041', 99, 2000000, 2000000, GETDATE(), GETDATE(), 1, 20, 1, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00042', 28, 360000, 360000, GETDATE(), GETDATE(), 1, 21, 2, 3);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00043', 12, 430000, 430000, GETDATE(), GETDATE(), 1, 1, 1, 4);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00044', 14, 78000, 78000, GETDATE(), GETDATE(), 1, 2, 2, 5);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00045', 223, 130000, 130000, GETDATE(), GETDATE(), 1, 3, 9, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00046', 333, 600000, 600000, GETDATE(), GETDATE(), 1, 4, 8, 3);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00047', 492, 1500000, 1500000, GETDATE(), GETDATE(), 1, 5, 2, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00048', 389, 234000, 234000, GETDATE(), GETDATE(), 1, 6, 2, 4);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00049', 123, 456000, 456000, GETDATE(), GETDATE(), 1, 7, 1, 2);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00050', 324, 4200000, 4200000, GETDATE(), GETDATE(), 1, 8, 2, 3);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00051', 34, 6780000, 6780000, GETDATE(), GETDATE(), 1, 22, 2, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00052', 51, 210000, 210000, GETDATE(), GETDATE(), 1, 23, 3, 3);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00053', 61, 65000, 65000, GETDATE(), GETDATE(), 1, 24, 2, 8);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00054', 73, 56000, 56000, GETDATE(), GETDATE(), 1, 25, 3, 5);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00055', 82, 23000, 23000, GETDATE(), GETDATE(), 1, 26, 2, 7);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00056', 23, 31000, 31000, GETDATE(), GETDATE(), 1, 27, 4, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00057', 73, 900000, 900000, GETDATE(), GETDATE(), 1, 28, 2, 2);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00058', 28, 230000, 230000, GETDATE(), GETDATE(), 1, 29, 5, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00059', 38, 5500000, 5500000, GETDATE(), GETDATE(), 1, 30, 3, 9);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00060', 38, 5600000, 5600000, GETDATE(), GETDATE(), 1, 9, 3, 9);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00061', 73, 900000, 900000, GETDATE(), GETDATE(), 1, 31, 2, 2);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00062', 28, 230000, 230000, GETDATE(), GETDATE(), 1, 31, 5, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00063', 38, 5500000, 5500000, GETDATE(), GETDATE(), 1, 32, 1, 7);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT00064', 38, 5600000, 5600000, GETDATE(), GETDATE(), 1, 32, 3, 9);


SELECT * FROM Image
    INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308249328.png', GETDATE(), GETDATE(), 1, 1);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308249331.png', GETDATE(), GETDATE(), 1, 1);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308058778.png', GETDATE(), GETDATE(), 1, 2);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308058786.png', GETDATE(), GETDATE(), 1, 2);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308058788.png', GETDATE(), GETDATE(), 1, 2);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308037329.png', GETDATE(), GETDATE(), 1, 3);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308037332.png', GETDATE(), GETDATE(), 1, 3);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308016053.png', GETDATE(), GETDATE(), 1, 4);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308016061.png', GETDATE(), GETDATE(), 1, 4);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308016063.png', GETDATE(), GETDATE(), 1, 4);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308118425.png', GETDATE(), GETDATE(), 1, 5);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308118428.png', GETDATE(), GETDATE(), 1, 5);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308118431.png', GETDATE(), GETDATE(), 1, 5);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308118432.png', GETDATE(), GETDATE(), 1, 5);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308118434.png', GETDATE(), GETDATE(), 1, 5);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308118437.png', GETDATE(), GETDATE(), 1, 5);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307928294.png', GETDATE(), GETDATE(), 1, 6);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307928296.png', GETDATE(), GETDATE(), 1, 6);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307928296.png', GETDATE(), GETDATE(), 1, 6);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308084680.png', GETDATE(), GETDATE(), 1, 7);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308084683.png', GETDATE(), GETDATE(), 1, 7);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307897284.png', GETDATE(), GETDATE(), 1, 8);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307897286.png', GETDATE(), GETDATE(), 1, 8);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307897288.png', GETDATE(), GETDATE(), 1, 8);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307897291.png', GETDATE(), GETDATE(), 1, 8);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307859901.png', GETDATE(), GETDATE(), 1, 9);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307859904.png', GETDATE(), GETDATE(), 1, 9);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307859907.png', GETDATE(), GETDATE(), 1, 9);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307835651.png', GETDATE(), GETDATE(), 1, 10);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307835654.png', GETDATE(), GETDATE(), 1, 10);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307835656.png', GETDATE(), GETDATE(), 1, 10);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307800132.png', GETDATE(), GETDATE(), 1, 11);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307800136.png', GETDATE(), GETDATE(), 1, 11);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307800137.png', GETDATE(), GETDATE(), 1, 11);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307770601.png', GETDATE(), GETDATE(), 1, 12);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307770604.png', GETDATE(), GETDATE(), 1, 12);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307770606.png', GETDATE(), GETDATE(), 1, 12);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307770608.png', GETDATE(), GETDATE(), 1, 12);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307770611.png', GETDATE(), GETDATE(), 1, 12);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307745200.png', GETDATE(), GETDATE(), 1, 13);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307745202.png', GETDATE(), GETDATE(), 1, 13);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307745204.png', GETDATE(), GETDATE(), 1, 13);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307745207.png', GETDATE(), GETDATE(), 1, 13);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307745209.png', GETDATE(), GETDATE(), 1, 13);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307706699.png', GETDATE(), GETDATE(), 1, 14);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307706701.png', GETDATE(), GETDATE(), 1, 14);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307706705.png', GETDATE(), GETDATE(), 1, 14);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307662348.png', GETDATE(), GETDATE(), 1, 15);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307662352.png', GETDATE(), GETDATE(), 1, 15);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307639492.png', GETDATE(), GETDATE(), 1, 16);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307639495.png', GETDATE(), GETDATE(), 1, 16);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307639497.png', GETDATE(), GETDATE(), 1, 16);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307619794.png', GETDATE(), GETDATE(), 1, 17);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307600459.png', GETDATE(), GETDATE(), 1, 18);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307600460.png', GETDATE(), GETDATE(), 1, 18);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307600461.png', GETDATE(), GETDATE(), 1, 18);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307600463.png', GETDATE(), GETDATE(), 1, 18);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308515739.png', GETDATE(), GETDATE(), 1, 19);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308515743.png', GETDATE(), GETDATE(), 1, 19);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307553550.png', GETDATE(), GETDATE(), 1, 20);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307553552.png', GETDATE(), GETDATE(), 1, 20);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307531702.png', GETDATE(), GETDATE(), 1, 21);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307531705.png', GETDATE(), GETDATE(), 1, 21);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307531708.png', GETDATE(), GETDATE(), 1, 21);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307531712.png', GETDATE(), GETDATE(), 1, 21);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307531715.png', GETDATE(), GETDATE(), 1, 21);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307507441.png', GETDATE(), GETDATE(), 1, 22);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307507445.png', GETDATE(), GETDATE(), 1, 22);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307507446.png', GETDATE(), GETDATE(), 1, 22);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307507448.png', GETDATE(), GETDATE(), 1, 22);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307507451.png', GETDATE(), GETDATE(), 1, 22);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307487414.png', GETDATE(), GETDATE(), 1, 23);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307487418.png', GETDATE(), GETDATE(), 1, 23);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307487418.png', GETDATE(), GETDATE(), 1, 23);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307467916.png', GETDATE(), GETDATE(), 1, 24);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307467918.png', GETDATE(), GETDATE(), 1, 24);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307467919.png', GETDATE(), GETDATE(), 1, 24);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307467922.png', GETDATE(), GETDATE(), 1, 24);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307433258.png', GETDATE(), GETDATE(), 1, 25);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307433260.png', GETDATE(), GETDATE(), 1, 25);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307409796.png', GETDATE(), GETDATE(), 1, 26);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307409799.png', GETDATE(), GETDATE(), 1, 26);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307409800.png', GETDATE(), GETDATE(), 1, 26);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307409802.png', GETDATE(), GETDATE(), 1, 26);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307380969.png', GETDATE(), GETDATE(), 1, 27);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307380972.png', GETDATE(), GETDATE(), 1, 27);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307380974.png', GETDATE(), GETDATE(), 1, 27);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307351073.png', GETDATE(), GETDATE(), 1, 28);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307351075.png', GETDATE(), GETDATE(), 1, 29);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307351077.png', GETDATE(), GETDATE(), 1, 28);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307351079.png', GETDATE(), GETDATE(), 1, 28);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307244798.png', GETDATE(), GETDATE(), 1, 29);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307244808.png', GETDATE(), GETDATE(), 1, 29);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705307244811.png', GETDATE(), GETDATE(), 1, 29);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308137313.png', GETDATE(), GETDATE(), 1, 30);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308137315.png', GETDATE(), GETDATE(), 1, 30);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308229169.png', GETDATE(), GETDATE(), 1, 31);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308229172.png', GETDATE(), GETDATE(), 1, 31);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308182123.png', GETDATE(), GETDATE(), 1, 32);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308182126.png', GETDATE(), GETDATE(), 1, 32);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308182128.png', GETDATE(), GETDATE(), 1, 32);
INSERT INTO Image (name, CreateDate, UpdateDate, Status, ProductId)
VALUES ('product_1705308182131.png', GETDATE(), GETDATE(), 1, 32);




SELECT * FROM Role
    INSERT INTO Role (name, CreateDate, UpdateDate, Status)
VALUES ('Admin', GETDATE(), GETDATE(), 1);
INSERT INTO Role (name, CreateDate, UpdateDate, Status)
VALUES ('Employee', GETDATE(), GETDATE(), 1);
INSERT INTO Role (name, CreateDate, UpdateDate, Status)
VALUES ('User', GETDATE(), GETDATE(), 1);

SELECT * FROM Account

    INSERT INTO Account(Name, Email, PhoneNumber, Password, Birthday, Avatar, CreateDate, UpdateDate, Status, RoleId)
VALUES (N'Trần Thanh Tùng', 'admin@gmail.com', '0943670235',
    '$2a$10$PHNpj3vmMEXFe.HByeH98uzXAk8pj9CLwQJUQxII3jbk/EpA/yJ4e', '2003-06-01 00:00:00.000', 'tomiokah3j4!@dn.png',
    '2023-12-17 19:18:55.303', '2023-12-17 19:18:55.303', 1, 1),
    (N'Phùng Minh Quân', 'quanpm@gmail.com', '0936163632',
    '$2a$10$nBJaDOAabttH8yE8pQUxpOXGLEXRP8/NKY8UhmxR5CP/YtGQLsKau', '1999-12-31 00:00:00.000', 'eren&90hnsd#.png',
    '2023-12-17 19:18:55.303', '2023-12-17 19:18:55.303', 1, 1),
    (N'Lương Xuân Hùng', 'hunglx@gmail.com', '0833390195',
    '$2a$12$UzOpuN1y3ODGkG2jCjBSf.ZT169QpOsL7nUdGFNskIscy9DV509qy', '2003-11-18 00:00:00.000', 'hinatahsjd#@#$HJ.png',
    '2023-12-17 19:18:55.303', '2023-12-17 19:18:55.303', 1, 2),
    (N'Chu Minh Tuấn', 'tuancm@gmail.com', '0982666189',
    '$2a$12$cxpoRAXvIoi8Zducezc6YOAh8eTvR9ZBTzz.YksZnfEN00QTTddjy', '2003-06-23 00:00:00.000', 'muichiro#@$dhsjfkGHJ.png',
    '2023-12-17 19:18:55.303', '2023-12-17 19:18:55.303', 1, 2),
    (N'Nguyễn Tuấn Hiếu', 'hieunt@gmail.com', '0977572350',
    '$2a$12$QXRS0HKa.qQiPPYrYh2cBOawwWQzHkWho1BdLQxWdHxd5rvExZopW', '2000-12-21 00:00:00.000', 'naruto$%^&&^fdhj12SD.png',
    '2023-12-17 19:18:55.303', '2023-12-17 19:18:55.303', 1, 3),
    (N'Nguyễn Văn Tú', 'tunv@gmail.com', '0769347798',
    '$2a$12$s/pfJpaQST110hwJfuK5KONpDc0YoAFPHhTS5u.c11dwwxurL/w7a', '1999-02-15 00:00:00.000', 'nezuko@#$Dsshjk_s.png',
    '2023-12-17 19:18:55.303', '2023-12-17 19:18:55.303', 1, 3),
    (N'Lê Minh Hiếu', 'hieulm@gmail.com', '0963360843',
    '$2a$12$WmHT5ni/SENjPvaGONRE/ORs/9Bzn0a4hJBuHLttJW3h4U6TGytSG', '1997-10-11 00:00:00.000', 'pain2347sjdhGHJ.png',
    '2023-12-17 19:18:55.303', '2023-12-17 19:18:55.303', 1, 3),
    (N'Nguyễn Huy Hoàng', 'hoangnh@gmail.com', '0377733070',
    '$2a$12$wyQjo6lHWV8pX/vRFITvzuE5EWJeqBKhapYbYVvBI.w6AuQEAu9eG', '2003-06-23 00:00:00.000', 'saitama@#$hsdgVBN.png',
    '2023-12-17 19:18:55.303', '2023-12-17 19:18:55.303', 1, 3),
    (N'Lê Minh Khuê', 'khuelm@gmail.com', '0588660409',
    '$2a$12$Pr2.Rcs5YazU8xs8tql4g.eSv2kP.ddC6q5nxhw2gsrehzlGUNU9y', '2002-11-11 00:00:00.000', 'songokuEWR3647#@v.png',
    '2023-12-17 19:18:55.303', '2023-12-17 19:18:55.303', 1, 3),
    (N'Trần Đại Lâm', 'lamtd@gmail.com', '0982666189',
    '$2a$12$B2Q/SPewPQhZ1prVGsl7zeBBuGWWFKmcfa/RxQTxbyN7KxBXoAZvG', '2001-07-23 00:00:00.000', 'tanjiro#$%dshHEWD.png',
    '2023-12-17 19:18:55.303', '2023-12-17 19:18:55.303', 1, 3);
